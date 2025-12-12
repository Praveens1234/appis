package com.appis.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appis.android.domain.model.PipelineStep
import com.appis.android.ui.components.ChatBubble
import com.appis.android.ui.components.GlassInput
import com.appis.android.ui.components.TypingIndicator
import com.appis.android.ui.components.graph.PipelineGraph
import com.appis.android.ui.theme.AppisColors
import com.appis.android.ui.viewmodel.ChatViewModel

@Composable
fun ChatScreen(
    onBack: () -> Unit,
    onViewCode: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val messages by viewModel.messages.collectAsState()
    val isTyping by viewModel.isTyping.collectAsState()
    val pipelineStep by viewModel.pipelineStep.collectAsState(initial = PipelineStep.INIT)
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (pipelineStep != PipelineStep.INIT) {
            PipelineGraph(currentStep = pipelineStep)
            
            // Show View Code button if we have code
            if (pipelineStep.ordinal >= PipelineStep.CODING.ordinal) {
                Spacer(modifier = Modifier.height(8.dp))
                com.appis.android.ui.components.NeonButton(
                    text = "View Code",
                    onClick = onViewCode,
                    modifier = Modifier.fillMaxWidth().height(36.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Chat History
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            item {
                if (isTyping) {
                    TypingIndicator(modifier = Modifier.padding(8.dp))
                }
            }
            items(messages.reversed()) { msg ->
                ChatBubble(
                    message = msg,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input Area
        GlassInput(
            value = inputText,
            onValueChange = { inputText = it },
            onSend = {
                if (inputText.isNotBlank()) {
                    viewModel.sendMessage(inputText)
                    inputText = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
