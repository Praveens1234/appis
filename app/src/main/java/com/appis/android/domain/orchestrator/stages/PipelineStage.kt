package com.appis.android.domain.orchestrator.stages

import com.appis.android.domain.model.PipelineStep

interface PipelineStage {
    val step: PipelineStep
    suspend fun execute(context: StageContext)
}

data class StageContext(
    val projectId: String,
    val apiKey: String,
    val input: Any? = null
)
