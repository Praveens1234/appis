package com.appis.android.data.remote

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ChatCompletionRequest(
    val model: String,
    val messages: List<Message>,
    val stream: Boolean = true,
    val temperature: Double = 0.7,
    val top_p: Double = 1.0,
    val max_tokens: Int = 4096
)

@Serializable
data class Message(
    val role: String,
    val content: String
)

@Serializable
data class ChatCompletionChunk(
    val id: String,
    val choices: List<ChunkChoice>
)

@Serializable
data class ChunkChoice(
    val index: Int,
    val delta: ChunkDelta,
    val finish_reason: String?
)

@Serializable
data class ChunkDelta(
    val content: String? = null,
    val role: String? = null
)
