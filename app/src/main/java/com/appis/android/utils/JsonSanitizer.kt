package com.appis.android.utils

import java.util.regex.Pattern

object JsonSanitizer {
    
    // Regex to find JSON block in markdown ```json ... ``` or just { ... }
    private val JSON_BLOCK_PATTERN = Pattern.compile("```json\\s*(\\{.*?\\})\\s*```", Pattern.DOTALL)
    private val BRACE_PATTERN = Pattern.compile("(\\{.*\\})", Pattern.DOTALL)

    fun sanitize(input: String): String {
        var clean = input.trim()
        
        // 1. Try to extract from markdown block
        val matcher = JSON_BLOCK_PATTERN.matcher(clean)
        if (matcher.find()) {
            return matcher.group(1)?.trim() ?: "{}"
        }

        // 2. Try to find first { and last }
        val braceMatcher = BRACE_PATTERN.matcher(clean)
        if (braceMatcher.find()) {
            return braceMatcher.group(1)?.trim() ?: "{}"
        }

        return clean
    }
}
