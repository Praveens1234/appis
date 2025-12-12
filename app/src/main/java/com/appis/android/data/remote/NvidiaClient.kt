package com.appis.android.data.remote

import com.appis.android.data.local.SettingsManager
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NvidiaClient @Inject constructor(
    private val client: OkHttpClient,
    private val settingsManager: SettingsManager
) {
    // Standard JSON media type
    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    // Overloaded for backward compatibility with Agent classes that pass apiKey manually,
    // but preferred usage is to get it from settings internally if not passed.
    // However, the Agent classes (Planner/Coder) pass it in. 
    // Wait, RealOrchestrator passes it in. 
    // Let's modify the signature to support both or just use the passed one.
    // The previous implementation ignored the passed apiKey in favor of the repository?
    // No, the previous implementation in RealOrchestrator passed `apiKey` to a method `generateResponse(apiKey, prompt, systemPrompt)`.
    // But this file `NvidiaClient.kt` shows `generateResponse(prompt, systemPrompt)` signature!
    // This means `RealOrchestrator` was calling a method that didn't exist in this version of the file, 
    // OR I am reading a version that is different from what I thought.
    // Ah, `RealOrchestrator` calls `nvidiaClient.generateResponse(apiKey = apiKey, ...)`
    // So I should add that parameter.

    suspend fun generateResponse(apiKey: String? = null, prompt: String, systemPrompt: String): String {
        val finalApiKey = apiKey ?: settingsManager.getNvidiaKey() ?: ""
        val model = "nvidia/llama-3.1-nemotron-70b-instruct" // Hardcoded "Real" model for now

        val messages = JSONArray()
            .put(JSONObject().put("role", "system").put("content", systemPrompt))
            .put(JSONObject().put("role", "user").put("content", prompt))

        val jsonBody = JSONObject()
            .put("model", model)
            .put("messages", messages)
            .put("stream", false)
            .put("max_tokens", 2048)

        val request = Request.Builder()
            .url("https://integrate.api.nvidia.com/v1/chat/completions")
            .header("Authorization", "Bearer $finalApiKey")
            .header("Content-Type", "application/json")
            .post(jsonBody.toString().toRequestBody(jsonMediaType))
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return "Error: ${response.code} - ${response.message}"
            val responseBody = response.body?.string() ?: return "Error: Empty Body"
            val json = JSONObject(responseBody)
            return json.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")
        }
    }
}
