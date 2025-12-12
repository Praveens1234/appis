package com.appis.android.domain.agents

import android.content.Context
import com.appis.android.utils.LogUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PromptManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val prompts = mutableMapOf<String, String>()

    init {
        loadPrompts()
    }

    private fun loadPrompts() {
        try {
            val assetManager = context.assets
            val inputStream = assetManager.open("prompts.json")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonString = reader.use { it.readText() }
            
            val jsonObject = JSONObject(jsonString)
            val keys = jsonObject.keys()
            
            while (keys.hasNext()) {
                val key = keys.next()
                prompts[key] = jsonObject.getString(key)
            }
            LogUtils.d("Prompts loaded successfully: ${prompts.keys}")
        } catch (e: Exception) {
            LogUtils.e("Failed to load prompts", e)
        }
    }

    fun getPrompt(key: String): String {
        return prompts[key] ?: throw IllegalArgumentException("Prompt key '$key' not found")
    }
}
