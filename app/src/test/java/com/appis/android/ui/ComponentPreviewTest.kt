package com.appis.android.ui

import app.cash.paparazzi.Paparazzi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.appis.android.ui.components.ChatBubble
import com.appis.android.ui.components.GlassCard
import com.appis.android.ui.components.NeonButton
import com.appis.android.ui.theme.AppisTheme
import org.junit.Rule
import org.junit.Test

class ComponentPreviewTest {
    @get:Rule
    val paparazzi = Paparazzi(
        theme = "android:Theme.Material.NoActionBar"
    )

    @Test
    fun testComponents() {
        paparazzi.snapshot {
            AppisTheme {
                Column(modifier = Modifier.padding(20.dp)) {
                    GlassCard {
                        Text("This is a Glass Card")
                    }
                    
                    NeonButton(
                        text = "Start Build",
                        onClick = {}
                    )

                    ChatBubble(
                        message = "Hello! I am Appis.",
                        isUser = false
                    )

                    ChatBubble(
                        message = "Let's build an app.",
                        isUser = true
                    )
                }
            }
        }
    }
}
