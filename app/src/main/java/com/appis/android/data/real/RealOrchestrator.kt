package com.appis.android.data.real

import com.appis.android.data.local.SettingsManager
import com.appis.android.data.remote.NvidiaClient
import com.appis.android.domain.model.ChatMessage
import com.appis.android.domain.model.PipelineStep
import com.appis.android.domain.model.Project
import com.appis.android.domain.model.ProjectStatus
import com.appis.android.domain.repository.OrchestratorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

import com.appis.android.data.local.ProjectDao
import kotlinx.coroutines.flow.firstOrNull

@Singleton
class RealOrchestrator @Inject constructor(
    private val nvidiaClient: NvidiaClient,
    private val settingsManager: SettingsManager,
    private val saasOrchestrator: com.appis.android.domain.orchestrator.SaasOrchestrator,
    private val eventBus: com.appis.android.domain.orchestrator.EventBus,
    private val projectDao: ProjectDao
) : OrchestratorRepository {

    private val _currentProject = MutableStateFlow<Project?>(null)
    override val currentProject = _currentProject.asStateFlow()

    private val _pipelineStep = MutableStateFlow(PipelineStep.INIT)
    override val pipelineStep = _pipelineStep.asStateFlow()

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    override val messages = _messages.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            // Restore last session
            val lastProjects = projectDao.getAllProjects().firstOrNull()
            if (!lastProjects.isNullOrEmpty()) {
                val entity = lastProjects.first()
                val status = try {
                    PipelineStep.valueOf(entity.status)
                } catch (e: Exception) {
                    PipelineStep.INIT
                }
                
                _currentProject.value = Project(
                    id = entity.id,
                    title = entity.name,
                    description = entity.description,
                    status = com.appis.android.domain.model.ProjectStatus.valueOf(if (status == PipelineStep.SUCCESS) "SUCCESS" else "RUNNING")
                )
                _pipelineStep.value = status
            }
        }

        scope.launch {
            saasOrchestrator.state.collect { state ->
                _pipelineStep.value = state.currentStep
                _currentProject.value = state.currentProject
            }
        }
        
        scope.launch {
            eventBus.events.collect { event ->
                when(event) {
                    is com.appis.android.domain.orchestrator.PipelineEvent.CodeGenerated -> {
                         addMessage("Generated: ${event.file}", false)
                    }
                    is com.appis.android.domain.orchestrator.PipelineEvent.AllCodeGenerated -> {
                        addMessage("All code generated for project ${event.projectId}", false)
                    }
                    is com.appis.android.domain.orchestrator.PipelineEvent.Error -> {
                        addMessage("Error: ${event.message}", false)
                    }
                    else -> {}
                }
            }
        }
    }

    override suspend fun setSimulationMode(enabled: Boolean) {
        settingsManager.setSimulationMode(enabled)
    }

    override suspend fun createProject(title: String, description: String) {
        val projectId = UUID.randomUUID().toString()
        val project = Project(
            id = projectId,
            title = title,
            description = description,
            status = ProjectStatus.RUNNING
        )
        _currentProject.value = project
        _pipelineStep.value = PipelineStep.PLANNING
        
        addMessage("Project Created. Starting Autonomous Pipeline...", false)
        
        scope.launch {
            val apiKey = settingsManager.getNvidiaKey()
            if (apiKey.isNullOrBlank()) {
                addMessage("Error: Nvidia API Key not found. Please set it in Settings.", false)
                _pipelineStep.value = PipelineStep.FAILED
                return@launch
            }

            saasOrchestrator.executePlan(projectId, apiKey, title, description)
            // _pipelineStep is now updated via state observation
        }
    }

    override suspend fun sendMessage(content: String) {
        addMessage(content, true)
        addMessage("I received your instruction. The pipeline is processing...", false)
    }

    private fun addMessage(content: String, isUser: Boolean) {
        val msg = ChatMessage(UUID.randomUUID().toString(), content, isUser)
        _messages.update { it + msg }
    }
}
