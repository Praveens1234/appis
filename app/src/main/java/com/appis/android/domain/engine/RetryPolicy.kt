package com.appis.android.domain.engine

import kotlinx.coroutines.delay
import java.io.IOException
import javax.inject.Inject

class RetryPolicy @Inject constructor() {
    suspend fun <T> execute(
        times: Int = 3,
        initialDelay: Long = 1000,
        factor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelay
        repeat(times - 1) {
            try {
                return block()
            } catch (e: IOException) {
                // Only retry on network IO issues
                delay(currentDelay)
                currentDelay = (currentDelay * factor).toLong()
            }
        }
        return block() // Last attempt
    }
}
