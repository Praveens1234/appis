package com.appis.android.domain.repository

import com.appis.android.domain.model.ChatMessage
import com.appis.android.domain.model.PipelineStep
import com.appis.android.domain.model.Project
import kotlinx.coroutines.flow.Flow

interface OrchestratorRepository {
    val currentProject: Flow<Project?>
    val pipelineStep: Flow<PipelineStep>
    val messages: Flow<List<ChatMessage>>

    suspend fun createProject(title: String, description: String)
    suspend fun sendMessage(content: String)
    suspend fun setSimulationMode(enabled: Boolean)
}
