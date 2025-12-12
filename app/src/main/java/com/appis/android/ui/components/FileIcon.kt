package com.appis.android.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FileIcon(
    fileName: String,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    val icon = when {
        fileName.endsWith(".kt") || fileName.endsWith(".java") -> Icons.Default.Code
        fileName.endsWith(".xml") || fileName.endsWith(".json") -> Icons.Default.Code
        fileName.endsWith(".png") || fileName.endsWith(".jpg") -> Icons.Default.Image
        !fileName.contains(".") -> Icons.Default.Folder
        else -> Icons.Default.Description
    }
    
    Icon(imageVector = icon, contentDescription = null, modifier = modifier, tint = tint)
}
