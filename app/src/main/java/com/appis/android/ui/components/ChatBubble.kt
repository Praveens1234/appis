package com.appis.android.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.appis.android.ui.theme.AppisColors
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.appis.android.ui.theme.glassEffect

@Composable
fun ChatBubble(
    message: ChatMessage,
    modifier: Modifier = Modifier
) {
    // Typewriter effect state
    val displayedText = remember { mutableStateOf("") }
    
    LaunchedEffect(message.content) {
        if (message.isUser) {
            displayedText.value = message.content
        } else {
            // Animate only for bot
            if (displayedText.value == message.content) return@LaunchedEffect
            
            for (i in 1..message.content.length) {
                displayedText.value = message.content.take(i)
                kotlinx.coroutines.delay(15) // Typing speed
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 300.dp)
                .glassEffect(
                    cornerRadius = 18.dp,
                    borderColor = if (message.isUser) Color.White.copy(alpha = 0.2f) else AppisColors.NeonCyan.copy(alpha = 0.5f)
                )
                .padding(16.dp)
        ) {
            Text(
                text = displayedText.value,
                color = AppisColors.GlassTextPrimary
            )
        }
    }
}
