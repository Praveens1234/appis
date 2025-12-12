package com.appis.android.ui.screens.code

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appis.android.ui.components.GlassCard
import com.appis.android.ui.components.syntax.SyntaxHighlighter
import com.appis.android.ui.theme.AppisColors

@Composable
fun CodeViewerScreen(
    fileName: String,
    code: String,
    onBack: () -> Unit
) {
    GlassCard(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column {
            Text(
                text = fileName,
                color = AppisColors.NeonCyan,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Box(modifier = Modifier.weight(1f)) {
                val scrollState = rememberScrollState()
                
                LazyColumn(modifier = Modifier.horizontalScroll(scrollState)) {
                    itemsIndexed(code.lines()) { index, line ->
                        Row {
                            Text(
                                text = "${index + 1} ",
                                color = Color.Gray,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp,
                                modifier = Modifier.width(32.dp)
                            )
                            SyntaxHighlighter(code = line)
                        }
                    }
                }
            }
        }
    }
}
