package com.appis.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appis.android.domain.model.ChatMessage
import com.appis.android.domain.orchestrator.ChatProcessor
import com.appis.android.domain.orchestrator.SaasOrchestrator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatProcessor: ChatProcessor,
    private val orchestrator: SaasOrchestrator
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages = _messages.asStateFlow()

    private val _isTyping = MutableStateFlow(false)
    val isTyping = _isTyping.asStateFlow()

    val pipelineStep = orchestrator.state.map { it.currentStep }

    fun sendMessage(content: String) {
        val userMsg = ChatMessage(UUID.randomUUID().toString(), content, true)
        _messages.update { it + userMsg }

        viewModelScope.launch {
            _isTyping.value = true
            val response = chatProcessor.processUserMessage(content)
            _isTyping.value = false
            
            val botMsg = ChatMessage(UUID.randomUUID().toString(), response, false)
            _messages.update { it + botMsg }
        }
    }
}
