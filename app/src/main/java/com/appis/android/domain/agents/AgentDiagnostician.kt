package com.appis.android.domain.agents

import com.appis.android.data.remote.NvidiaClient
import com.appis.android.utils.JsonSanitizer
import com.appis.android.utils.LogUtils
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AgentDiagnostician @Inject constructor(
    private val nvidiaClient: NvidiaClient,
    private val promptManager: PromptManager,
    private val gson: Gson
) {
    suspend fun diagnose(apiKey: String, errorLog: String, fileList: List<String>): DiagnosisResult {
        LogUtils.agent("Diagnostician", "Analyzing error log...")
        
        val systemPrompt = promptManager.getPrompt("diagnostician")
        val userPrompt = """
            Error Log:
            $errorLog
            
            Available Files:
            ${fileList.take(50).joinToString("\n")}
        """.trimIndent()

        try {
            val response = nvidiaClient.generateResponse(apiKey, userPrompt, systemPrompt)
            val sanitizedJson = JsonSanitizer.sanitize(response)
            LogUtils.agent("Diagnostician", "Diagnosis Plan: $sanitizedJson")
            return gson.fromJson(sanitizedJson, DiagnosisResult::class.java)
        } catch (e: Exception) {
            LogUtils.e("Diagnostician failed", e)
            // Fallback: Return empty or generic error
            return DiagnosisResult("Unknown Error", emptyList())
        }
    }
}

data class DiagnosisResult(
    val root_cause: String,
    val action_plan: List<CorrectionTask>
)

data class CorrectionTask(
    val file_path: String,
    val instruction: String
)
