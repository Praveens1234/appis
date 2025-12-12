package com.appis.android.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.appis.android.ui.theme.AppisColors

@Composable
fun LoadingState(
    message: String,
    modifier: Modifier = Modifier
) {
    androidx.compose.foundation.layout.Column(
        modifier = modifier,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        CircularProgressIndicator(color = AppisColors.NeonCyan)
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))
        Text(text = message, color = AppisColors.GlassTextSecondary)
    }
}
