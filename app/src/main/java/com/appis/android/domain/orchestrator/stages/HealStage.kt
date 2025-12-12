package com.appis.android.domain.orchestrator.stages

import com.appis.android.domain.agents.AgentCorrector
import javax.inject.Inject

class HealStage @Inject constructor(
    private val agentCorrector: AgentCorrector
) {
    suspend fun execute(code: String, error: String, apiKey: String): String {
        return agentCorrector.fixCode(apiKey, code, error)
    }
}
