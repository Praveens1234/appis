package com.appis.android.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class KnowledgeBase(
    val projectId: String,
    val projectTitle: String,
    val projectDescription: String,
    val entries: MutableList<KBEntry> = mutableListOf()
)

@Serializable
data class KBEntry(
    val id: String,
    val type: KBType, // PLAN, ERROR, LOG, SNIPPET
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val summary: String? = null
)

enum class KBType {
    PLAN, ERROR, LOG, SNIPPET, AGENT_ACTION
}
