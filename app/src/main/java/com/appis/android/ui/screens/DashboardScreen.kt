package com.appis.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.appis.android.ui.components.GlassCard
import com.appis.android.ui.components.NeonButton
import com.appis.android.ui.theme.AppisColors
import com.appis.android.ui.theme.AppisTypography

@Composable
fun DashboardScreen(
    onCreateProject: () -> Unit,
    onViewExisting: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 48.dp, bottom = 24.dp)
            ) {
                Text(
                    text = "Appis",
                    style = AppisTypography.displayLarge,
                    color = AppisColors.NeonCyan
                )
                Text(
                    text = "Autonomous Builder",
                    style = AppisTypography.titleMedium,
                    color = AppisColors.GlassTextSecondary
                )
            }
        }

        // Actions
        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "Start Here",
                        style = AppisTypography.headlineSmall,
                        color = AppisColors.GlassTextPrimary
                    )
                    
                    NeonButton(
                        text = "Create New Project",
                        onClick = onCreateProject,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    NeonButton(
                        text = "View Existing Projects",
                        onClick = onViewExisting,
                        color = AppisColors.NeonPurple,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        
        // Status Indicators
        item {
             Row(
                 modifier = Modifier.fillMaxWidth(),
                 horizontalArrangement = Arrangement.SpaceEvenly
             ) {
                 StatusBadge(label = "Nvidia API", isActive = true)
                 StatusBadge(label = "GitHub", isActive = false)
             }
        }

        // Recent Projects Placeholder
        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        text = "Recent Projects",
                        style = AppisTypography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = "No recent projects found.",
                        style = AppisTypography.bodyMedium,
                        color = AppisColors.GlassTextSecondary
                    )
                }
            }
        }
    }
}

@Composable
fun StatusBadge(label: String, isActive: Boolean) {
    GlassCard(
        modifier = Modifier.padding(4.dp),
        blurRadius = 10.dp,
        cornerRadius = 16.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = if (isActive) AppisColors.NeonGreen else AppisColors.NeonRed,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, style = AppisTypography.labelSmall)
        }
    }
}
