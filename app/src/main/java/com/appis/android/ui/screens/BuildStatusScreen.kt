package com.appis.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.appis.android.ui.components.GlassCard
import com.appis.android.ui.theme.AppisColors
import com.appis.android.ui.theme.AppisTypography

@Composable
fun BuildStatusScreen(
    onBack: () -> Unit
) {
    // Basic placeholder for now, connecting to real build logs is complex
    // without a running build in this session.
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text("Build Status", style = AppisTypography.headlineMedium, color = AppisColors.NeonCyan)
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "No active builds. Connect GitHub to see status.",
                    color = AppisColors.GlassTextPrimary
                )
            }
        }
    }
}
