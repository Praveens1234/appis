package com.appis.android.data.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.InputStreamReader

object SseParser {
    fun parse(responseBody: ResponseBody): Flow<String> = flow {
        val inputStream = responseBody.byteStream()
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String? = reader.readLine()
        
        while (line != null) {
            if (line.startsWith("data: ")) {
                val data = line.removePrefix("data: ").trim()
                if (data != "[DONE]") {
                    emit(data)
                }
            }
            line = reader.readLine()
        }
    }
}
