package com.appis.android.domain.agents

import com.appis.android.data.remote.NvidiaClient
import com.appis.android.utils.LogUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AgentCoder @Inject constructor(
    private val nvidiaClient: NvidiaClient,
    private val promptManager: PromptManager
) {
    suspend fun writeCode(apiKey: String, filePlan: FilePlan, context: String = ""): String {
        LogUtils.agent("Coder", "Writing code for: ${filePlan.path}")
        
        val systemPrompt = promptManager.getPrompt("coder")
        val userPrompt = """
            File Path: ${filePlan.path}
            Description: ${filePlan.description}
            Context: $context
        """.trimIndent()

        try {
            val response = nvidiaClient.generateResponse(apiKey, userPrompt, systemPrompt)
            // Coder prompt says "Return ONLY the raw code", but we should strip markdown just in case
            val code = cleanCodeBlock(response)
            return code
        } catch (e: Exception) {
            LogUtils.e("Coder failed for ${filePlan.path}", e)
            return "// Error generating code: ${e.message}"
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
