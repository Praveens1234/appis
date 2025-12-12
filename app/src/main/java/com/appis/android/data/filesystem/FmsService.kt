package com.appis.android.data.filesystem

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FmsService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val rootDir: File by lazy {
        File(context.filesDir, "projects").apply { mkdirs() }
    }

    fun createProjectDir(projectId: String): File {
        val dir = File(rootDir, projectId)
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    fun writeFile(projectId: String, relativePath: String, content: String) {
        val projectDir = createProjectDir(projectId)
        val file = File(projectDir, relativePath)
        file.parentFile?.mkdirs()
        
        FileOutputStream(file).use {
            it.write(content.toByteArray())
        }
    }

    fun readFile(projectId: String, relativePath: String): String? {
        val file = File(createProjectDir(projectId), relativePath)
        if (!file.exists()) return null
        return file.readText()
    }
    
    fun getProjectRoot(projectId: String): File = createProjectDir(projectId)
}
