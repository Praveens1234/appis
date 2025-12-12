package com.appis.android.domain.engine

import com.appis.android.utils.LogUtils
import javax.inject.Inject

class OutputValidator @Inject constructor() {
    fun validateKotlin(code: String): Boolean {
        // Basic brace matching
        var braceCount = 0
        for (char in code) {
            when (char) {
                '{' -> braceCount++
                '}' -> braceCount--
            }
        }
        if (braceCount != 0) {
            LogUtils.e("OutputValidator", null)
            return false
        }
        return true
    }
}
