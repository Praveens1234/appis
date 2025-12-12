package com.appis.android.domain.agents

import com.appis.android.data.remote.NvidiaClient
import com.appis.android.utils.LogUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AgentCorrector @Inject constructor(
    private val nvidiaClient: NvidiaClient,
    private val promptManager: PromptManager
) {
    suspend fun fixCode(apiKey: String, code: String, errors: String): String {
        LogUtils.agent("Corrector", "Fixing code...")
        
        val systemPrompt = promptManager.getPrompt("corrector")
        val userPrompt = """
            Broken Code:
            $code
            
            Errors:
            $errors
        """.trimIndent()

        try {
            val response = nvidiaClient.generateResponse(apiKey, userPrompt, systemPrompt)
            return cleanCodeBlock(response)
        } catch (e: Exception) {
            LogUtils.e("Corrector failed", e)
            return code // Return original if fix fails
        }
    }

    private fun cleanCodeBlock(input: String): String {
        var code = input.trim()
        if (code.startsWith("```")) {
            val firstLineBreak = code.indexOf('\n')
            if (firstLineBreak != -1) {
                code = code.substring(firstLineBreak + 1)
            }
        }
        if (code.endsWith("```")) {
            code = code.substring(0, code.length - 3)
        }
        return code.trim()
    }
}
