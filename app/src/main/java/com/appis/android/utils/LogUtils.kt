package com.appis.android.utils

import android.util.Log

object LogUtils {
    private const val TAG = "AppisAgent"

    fun d(message: String) {
        try {
            Log.d(TAG, message)
        } catch (e: RuntimeException) {
            println("$TAG [DEBUG]: $message")
        }
    }

    fun e(message: String, throwable: Throwable? = null) {
        try {
            Log.e(TAG, message, throwable)
        } catch (e: RuntimeException) {
            println("$TAG [ERROR]: $message")
            throwable?.printStackTrace()
        }
    }
    
    fun agent(agentName: String, thought: String) {
        try {
            Log.i(TAG, "[$agentName]: $thought")
        } catch (e: RuntimeException) {
            println("$TAG [AGENT - $agentName]: $thought")
        }
    }
}
