package com.appis.android.domain.orchestrator.stages

import com.appis.android.data.filesystem.FmsService
import com.appis.android.domain.agents.AgentAuditor
import com.appis.android.domain.orchestrator.EventBus
import com.appis.android.domain.orchestrator.PipelineEvent
import javax.inject.Inject

class AuditStage @Inject constructor(
    private val agentAuditor: AgentAuditor,
    private val fmsService: FmsService,
    private val eventBus: EventBus
) {
    suspend fun execute(projectId: String, apiKey: String) {
        val root = fmsService.getProjectRoot(projectId)
        val files = root.walkTopDown().filter { it.isFile && it.extension == "kt" }.toList()
        
        if (files.isEmpty()) {
            eventBus.emit(PipelineEvent.Error("No files generated to audit."))
            return
        }

        // 1. Static Check for Error Markers
        val errorFile = files.find { it.readText().contains("// Error generating code") }
        if (errorFile != null) {
            eventBus.emit(PipelineEvent.AuditFailed(projectId, "Generation failed in ${errorFile.name}"))
            return
        }

        // 2. AI Audit on Main Activity (Critical Path)
        val mainActivity = files.find { it.name == "MainActivity.kt" }
        if (mainActivity != null) {
            val result = agentAuditor.auditCode(apiKey, mainActivity.readText(), mainActivity.path)
            if (result.status == "FAIL") {
                val issues = result.issues.joinToString { "${it.severity}: ${it.message}" }
                eventBus.emit(PipelineEvent.AuditFailed(projectId, issues))
                return
            }
        }

        eventBus.emit(PipelineEvent.AuditPassed(projectId))
    }
}
