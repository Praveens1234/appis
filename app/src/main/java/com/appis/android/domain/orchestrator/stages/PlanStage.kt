package com.appis.android.domain.orchestrator.stages

import com.appis.android.domain.agents.AgentPlanner
import com.appis.android.domain.model.Project
import com.appis.android.domain.orchestrator.EventBus
import com.appis.android.domain.orchestrator.PipelineEvent
import javax.inject.Inject

class PlanStage @Inject constructor(
    private val agentPlanner: AgentPlanner,
    private val eventBus: EventBus
) {
    suspend fun execute(project: Project, apiKey: String) {
        val plan = agentPlanner.generatePlan(apiKey, project.description)
        if (plan != null) {
            eventBus.emit(PipelineEvent.PlanCompleted(plan))
        } else {
            // Handle error
        }
    }
}
