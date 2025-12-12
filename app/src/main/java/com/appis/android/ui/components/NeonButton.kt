package com.appis.android.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.appis.android.ui.theme.AppisColors
import com.appis.android.ui.theme.glassEffect
import com.appis.android.ui.theme.neonGlow

@Composable
fun NeonButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = AppisColors.NeonCyan,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "buttonScale")

    Box(
        modifier = modifier
            .scale(scale)
            .neonGlow(color = if (enabled) color else Color.Gray)
            .glassEffect(borderColor = if (enabled) color else Color.Gray)
            .clickable(
                interactionSource = interactionSource,
                indication = null, // Custom indication handled by scale/glow
                enabled = enabled,
                onClick = onClick
            )
            .padding(horizontal = 24.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (enabled) AppisColors.GlassTextPrimary else AppisColors.GlassTextSecondary,
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
    }
}
