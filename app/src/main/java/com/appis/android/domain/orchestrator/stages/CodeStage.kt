package com.appis.android.domain.orchestrator.stages

import com.appis.android.data.filesystem.FmsService
import com.appis.android.domain.agents.AgentCoder
import com.appis.android.domain.agents.ProjectStructure
import com.appis.android.domain.orchestrator.EventBus
import com.appis.android.domain.orchestrator.PipelineEvent
import javax.inject.Inject

class CodeStage @Inject constructor(
    private val agentCoder: AgentCoder,
    private val fmsService: FmsService,
    private val eventBus: EventBus
) {
    suspend fun execute(plan: ProjectStructure, apiKey: String, projectId: String) {
        plan.structure.forEach { filePlan ->
            val code = agentCoder.writeCode(apiKey, filePlan)
            fmsService.writeFile(projectId, filePlan.path, code)
            eventBus.emit(PipelineEvent.CodeGenerated(filePlan.path))
        }
        eventBus.emit(PipelineEvent.AllCodeGenerated(projectId))
    }
}
