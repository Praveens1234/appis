package com.appis.android.domain.orchestrator

import com.appis.android.data.local.ProjectDao
import com.appis.android.data.local.ProjectEntity
import com.appis.android.data.local.SettingsManager
import com.appis.android.domain.model.PipelineStep
import com.appis.android.domain.model.Project
import com.appis.android.domain.orchestrator.stages.AuditStage
import com.appis.android.data.filesystem.FmsService
import com.appis.android.domain.orchestrator.stages.*
import com.appis.android.domain.orchestrator.stages.SyncStage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaasOrchestrator @Inject constructor(
    private val eventBus: EventBus,
    private val planStage: PlanStage,
    private val codeStage: CodeStage,
    private val auditStage: AuditStage,
    private val syncStage: SyncStage,
    private val buildStage: BuildStage,
    private val diagnosisStage: DiagnosisStage,
    private val healStage: HealStage,
    private val fmsService: FmsService,
    private val settingsManager: SettingsManager,
    private val projectDao: ProjectDao
) {
    private val _state = MutableStateFlow(PipelineState())
    val state = _state.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            eventBus.events.collect { event ->
                // Launch in a new coroutine to prevent blocking the collector
                launch {
                    handleEvent(event)
                }
            }
        }
    }

    private suspend fun handleEvent(event: PipelineEvent) {
        val apiKey = settingsManager.getNvidiaKey() ?: ""
        
        when (event) {
            is PipelineEvent.StartPlanning -> {
                updateStep(PipelineStep.PLANNING)
                if (apiKey.isBlank()) {
                    eventBus.emit(PipelineEvent.Error("Missing Nvidia API Key"))
                    return
                }
                planStage.execute(event.project, apiKey)
            }
            is PipelineEvent.PlanCompleted -> {
                updateStep(PipelineStep.CODING)
                val projectId = _state.value.currentProject?.id ?: ""
                codeStage.execute(event.plan, apiKey, projectId)
            }
            is PipelineEvent.AllCodeGenerated -> {
                updateStep(PipelineStep.AUDITING)
                auditStage.execute(event.projectId, apiKey)
            }
            is PipelineEvent.AuditPassed -> {
                updateStep(PipelineStep.SYNC)
                val project = _state.value.currentProject
                if (project != null) {
                    val token = settingsManager.getGithubToken() ?: ""
                    val username = settingsManager.getGithubUsername() ?: ""
                    
                    if (token.isBlank() || username.isBlank()) {
                         eventBus.emit(PipelineEvent.Error("Missing GitHub Token or Username"))
                         return
                    }
                    val success = syncStage.execute(token, project.id, project.title, username)
                    if (success) {
                        eventBus.emit(PipelineEvent.SyncCompleted("https://github.com/$username/${project.title.lowercase().replace(" ","-")}"))
                    } else {
                        eventBus.emit(PipelineEvent.Error("Sync Failed. Check Github Token/Username."))
                    }
                }
            }
            is PipelineEvent.SyncCompleted -> {
                updateStep(PipelineStep.BUILD)
                eventBus.emit(PipelineEvent.BuildStarted)
            }
            is PipelineEvent.BuildStarted -> {
                updateStep(PipelineStep.BUILD)
                // Trigger GitHub Action
                val project = _state.value.currentProject
                if (project != null) {
                    val token = settingsManager.getGithubToken() ?: ""
                    val username = settingsManager.getGithubUsername() ?: ""
                    
                    if (token.isBlank() || username.isBlank()) {
                         eventBus.emit(PipelineEvent.Error("Missing GitHub Token or Username for Build"))
                         return
                    }

                    // BuildStage takes repoName, which is sanitized title inside SyncStage. 
                    val repoName = project.title.lowercase().replace(" ", "-")
                    buildStage.execute(token, username, repoName)
                }
            }
            is PipelineEvent.BuildFailed -> {
                updateStep(PipelineStep.HEALING)
                // Gather file list
                val project = _state.value.currentProject
                val fileList = if (project != null) {
                    val root = fmsService.getProjectRoot(project.id)
                    root.walkTopDown().filter { it.isFile }.map { it.path }.toList()
                } else emptyList()
                
                val diagnosis = diagnosisStage.execute(apiKey, event.errorLog, fileList)
                eventBus.emit(PipelineEvent.DiagnosisCompleted(diagnosis))
            }
            is PipelineEvent.DiagnosisCompleted -> {
                // Execute healing loop
                val diagnosis = event.diagnosis
                val project = _state.value.currentProject
                
                if (project != null) {
                    diagnosis.action_plan.forEach { task ->
                        val currentContent = fmsService.readFile(project.id, task.file_path) ?: ""
                        val fixedContent = healStage.execute(currentContent, task.instruction, apiKey)
                        fmsService.writeFile(project.id, task.file_path, fixedContent)
                    }
                    // Retry build
                    eventBus.emit(PipelineEvent.HealingCompleted(true))
                }
            }
            is PipelineEvent.HealingCompleted -> {
                // Retry build
                eventBus.emit(PipelineEvent.BuildStarted)
            }
            is PipelineEvent.Error -> {
                updateStep(PipelineStep.FAILED)
            }
            else -> {}
        }
    }

    private suspend fun updateStep(step: PipelineStep) {
        _state.value = _state.value.copy(currentStep = step)
        // Update persistence
        val currentProject = _state.value.currentProject
        if (currentProject != null) {
            projectDao.updateStatus(currentProject.id, step.name)
        }
    }

    suspend fun startPipeline(project: Project) {
        _state.value = _state.value.copy(
            currentStep = PipelineStep.INIT,
            currentProject = project
        )
        
        // Save initial state to DB
        projectDao.insertProject(
            ProjectEntity(
                id = project.id,
                name = project.title,
                description = project.description,
                status = PipelineStep.INIT.name
            )
        )

        // Trigger planning stage via EventBus or direct call
        eventBus.emit(PipelineEvent.StartPlanning(project))
    }

    // Method expected by RealOrchestrator
    suspend fun executePlan(projectId: String, apiKey: String, title: String, description: String) {
        // Logic to bridge old call to new pipeline
        // For now, just start the pipeline
        startPipeline(
            Project(
                id = projectId,
                title = title,
                description = description,
                status = com.appis.android.domain.model.ProjectStatus.RUNNING
            )
        )
    }
}

data class PipelineState(
    val currentStep: PipelineStep = PipelineStep.INIT,
    val currentProject: Project? = null,
    val isRunning: Boolean = false
)
