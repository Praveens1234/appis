package com.appis.android.data.local

import com.appis.android.domain.model.KBEntry
import com.appis.android.domain.model.KBType
import com.appis.android.domain.model.KnowledgeBase
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KnowledgeBaseRepository @Inject constructor(
    private val fileManager: FileManager
) {
    private val json = Json { prettyPrint = true }

    fun getKB(projectId: String): KnowledgeBase {
        val jsonString = fileManager.readFile(projectId, "knowledge_base.json")
        return if (jsonString != null) {
            try {
                json.decodeFromString(jsonString)
            } catch (e: Exception) {
                // Fallback or empty
                KnowledgeBase(projectId, "Unknown", "Error loading")
            }
        } else {
            KnowledgeBase(projectId, "New Project", "")
        }
    }

    fun saveKB(kb: KnowledgeBase) {
        val jsonString = json.encodeToString(kb)
        fileManager.writeFile(kb.projectId, "knowledge_base.json", jsonString)
    }

    fun addEntry(projectId: String, type: KBType, content: String, summary: String? = null) {
        val kb = getKB(projectId)
        kb.entries.add(
            KBEntry(
                id = java.util.UUID.randomUUID().toString(),
                type = type,
                content = content,
                summary = summary
            )
        )
        saveKB(kb)
    }
    
    fun initKB(projectId: String, title: String, description: String) {
        val kb = KnowledgeBase(projectId, title, description)
        saveKB(kb)
    }
}
