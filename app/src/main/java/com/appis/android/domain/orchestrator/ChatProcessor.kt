package com.appis.android.domain.orchestrator

import com.appis.android.domain.model.ChatMessage
import com.appis.android.utils.LogUtils
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatProcessor @Inject constructor() {
    
    suspend fun processUserMessage(message: String): String {
        // Simple echo or intent classification for now
        LogUtils.d("Processing message: $message")
        if (message.contains("create", ignoreCase = true)) {
            return "INTENT_CREATE_PROJECT"
        }
        return "INTENT_CHAT"
    }
}
