package com.appis.android.ui.components.graph

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.appis.android.domain.model.PipelineStep
import com.appis.android.ui.theme.AppisColors
import com.appis.android.ui.theme.AppisTypography

@Composable
fun PipelineGraph(
    currentStep: PipelineStep,
    modifier: Modifier = Modifier
) {
    val steps = listOf(
        PipelineStep.PLANNING,
        PipelineStep.CODING,
        PipelineStep.AUDITING,
        PipelineStep.SYNC,
        PipelineStep.BUILD,
        PipelineStep.SUCCESS
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, step ->
            val isActive = currentStep.ordinal >= step.ordinal
            val isCurrent = currentStep == step
            
            PipelineNode(
                step = step,
                isActive = isActive,
                isCurrent = isCurrent,
                showConnector = index < steps.size - 1
            )
        }
    }
}

@Composable
fun PipelineNode(
    step: PipelineStep,
    isActive: Boolean,
    isCurrent: Boolean,
    showConnector: Boolean
) {
    val color by animateColorAsState(
        if (isActive) AppisColors.NeonCyan else AppisColors.GlassTextSecondary.copy(alpha = 0.3f)
    )
    
    val scale by animateFloatAsState(if (isCurrent) 1.2f else 1.0f)

    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(12.dp * scale)
                    .background(color, CircleShape)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = step.name.take(3), // Abbreviate
                style = AppisTypography.labelSmall,
                color = if (isActive) AppisColors.GlassTextPrimary else AppisColors.GlassTextSecondary
            )
        }
        
        if (showConnector) {
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(2.dp)
                    .background(color.copy(alpha = 0.5f))
            )
        }
    }
}
