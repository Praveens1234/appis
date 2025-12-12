package com.appis.android.domain.agents

import com.appis.android.data.remote.NvidiaClient
import com.appis.android.utils.JsonSanitizer
import com.appis.android.utils.LogUtils
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AgentAuditor @Inject constructor(
    private val nvidiaClient: NvidiaClient,
    private val promptManager: PromptManager,
    private val gson: Gson
) {
    suspend fun auditCode(apiKey: String, code: String, filePath: String): AuditResult {
        LogUtils.agent("Auditor", "Auditing: $filePath")
        
        val systemPrompt = promptManager.getPrompt("auditor")
        val userPrompt = """
            File Path: $filePath
            Code Content:
            $code
        """.trimIndent()

        try {
            val response = nvidiaClient.generateResponse(apiKey, userPrompt, systemPrompt)
            val sanitizedJson = JsonSanitizer.sanitize(response)
            return gson.fromJson(sanitizedJson, AuditResult::class.java)
        } catch (e: Exception) {
            LogUtils.e("Auditor failed", e)
            return AuditResult("UNKNOWN", emptyList())
        }
    }
}

data class AuditResult(
    val status: String, // PASS or FAIL
    val issues: List<AuditIssue>
)

data class AuditIssue(
    val severity: String,
    val file: String,
    val message: String
)
