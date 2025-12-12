package com.appis.android.domain.orchestrator

import com.appis.android.domain.agents.ProjectStructure
import com.appis.android.domain.model.Project
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventBus @Inject constructor() {
    // Increase buffer capacity to prevent deadlock when emitting from inside a collector
    private val _events = MutableSharedFlow<PipelineEvent>(extraBufferCapacity = 64)
    val events = _events.asSharedFlow()

    suspend fun emit(event: PipelineEvent) {
        _events.emit(event)
    }
}

sealed class PipelineEvent {
    data class StartPlanning(val project: Project) : PipelineEvent()
    data class PlanCompleted(val plan: ProjectStructure) : PipelineEvent()
    data class CodeGenerated(val file: String) : PipelineEvent()
    data class AllCodeGenerated(val projectId: String) : PipelineEvent()
    data class AuditPassed(val projectId: String) : PipelineEvent()
    data class AuditFailed(val projectId: String, val issues: String) : PipelineEvent()
    data class SyncCompleted(val repoUrl: String) : PipelineEvent()
    object BuildStarted : PipelineEvent()
    data class BuildFinished(val success: Boolean, val artifactUrl: String? = null) : PipelineEvent()
    data class BuildFailed(val errorLog: String) : PipelineEvent()
    data class DiagnosisCompleted(val diagnosis: com.appis.android.domain.agents.DiagnosisResult) : PipelineEvent()
    data class HealingCompleted(val success: Boolean) : PipelineEvent()
    data class Error(val message: String) : PipelineEvent()
}
