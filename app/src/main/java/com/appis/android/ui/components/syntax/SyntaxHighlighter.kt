package com.appis.android.ui.components.syntax

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.appis.android.ui.theme.AppisColors
import com.appis.android.ui.theme.AppisTypography

@Composable
fun SyntaxHighlighter(
    code: String,
    modifier: Modifier = Modifier
) {
    // Very basic syntax highlighting for now
    val annotatedString = buildAnnotatedString {
        val keywords = listOf("fun", "val", "var", "class", "package", "import", "return", "if", "else")
        
        code.split(Regex("(?<=\\s)|(?=\\s)")).forEach { word ->
            if (keywords.contains(word.trim())) {
                withStyle(style = SpanStyle(color = AppisColors.NeonPurple)) {
                    append(word)
                }
            } else {
                withStyle(style = SpanStyle(color = AppisColors.GlassTextPrimary)) {
                    append(word)
                }
            }
        }
    }

    Text(
        text = annotatedString,
        style = AppisTypography.bodySmall,
        modifier = modifier
    )
}
