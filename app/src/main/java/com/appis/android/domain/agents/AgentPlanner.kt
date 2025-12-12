package com.appis.android.domain.agents

import com.appis.android.data.remote.NvidiaClient
import com.appis.android.utils.JsonSanitizer
import com.appis.android.utils.LogUtils
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AgentPlanner @Inject constructor(
    private val nvidiaClient: NvidiaClient,
    private val promptManager: PromptManager,
    private val gson: Gson
) {
    suspend fun generatePlan(apiKey: String, projectDescription: String): ProjectStructure? {
        LogUtils.agent("Planner", "Generating plan for: $projectDescription")
        
        val systemPrompt = promptManager.getPrompt("planner")
        val userPrompt = "Project Requirements: $projectDescription"

        try {
            val response = nvidiaClient.generateResponse(apiKey, userPrompt, systemPrompt)
            val sanitizedJson = JsonSanitizer.sanitize(response)
            LogUtils.agent("Planner", "Raw Plan: $sanitizedJson")
            
            return gson.fromJson(sanitizedJson, ProjectStructure::class.java)
        } catch (e: Exception) {
            LogUtils.e("Planner failed", e)
            return null
        }
    }
}

data class ProjectStructure(
    val project_name: String,
    val package_name: String,
    val structure: List<FilePlan>
)

data class FilePlan(
    val path: String,
    val description: String
)
