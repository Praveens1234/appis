package com.appis.android.domain.orchestrator.stages

import com.appis.android.domain.agents.AgentDiagnostician
import com.appis.android.domain.agents.DiagnosisResult
import javax.inject.Inject

class DiagnosisStage @Inject constructor(
    private val agentDiagnostician: AgentDiagnostician
) {
    suspend fun execute(apiKey: String, errorLog: String, fileList: List<String>): DiagnosisResult {
        return agentDiagnostician.diagnose(apiKey, errorLog, fileList)
    }
}
