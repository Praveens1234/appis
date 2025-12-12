package com.appis.android.domain.engine

import com.appis.android.utils.LogUtils
import javax.inject.Inject

class HallucinationGuard @Inject constructor() {
    private val forbiddenSymbols = listOf(
        "com.example", // Avoid generic package
        "PLACEHOLDER",
        "TODO"
    )

    fun check(code: String): Boolean {
        forbiddenSymbols.forEach { symbol ->
            if (code.contains(symbol)) {
                LogUtils.agent("Guard", "Found forbidden symbol: $symbol")
                return false
            }
        }
        return true
    }
}
