package com.appis.android.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.appis.android.ui.theme.AppisColors
import com.appis.android.ui.theme.AppisTypography
import com.appis.android.ui.theme.glassEffect

@Composable
fun GlassInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean = true,
    minLines: Int = 1,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .glassEffect(alpha = 0.05f, cornerRadius = 12.dp)
            .padding(16.dp)
    ) {
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                color = AppisColors.GlassTextSecondary,
                style = AppisTypography.bodyMedium
            )
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = AppisTypography.bodyMedium.copy(color = AppisColors.GlassTextPrimary),
            cursorBrush = SolidColor(AppisColors.NeonCyan),
            singleLine = singleLine,
            minLines = minLines,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
