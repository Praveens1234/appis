package com.appis.android.domain.engine

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor() {
    private val MAX_TOKENS = 4096 // Conservative limit

    fun estimateTokens(text: String): Int {
        // Rough estimate: 1 token ~= 4 chars
        return text.length / 4
    }

    fun isSafe(text: String): Boolean {
        return estimateTokens(text) < MAX_TOKENS
    }
}
