package com.appis.android.data.filesystem

import java.io.File
import javax.inject.Inject

class FileTreeBuilder @Inject constructor() {
    
    fun buildTree(rootDir: File): TreeNode {
        return buildNode(rootDir)
    }

    private fun buildNode(file: File): TreeNode {
        val children = if (file.isDirectory) {
            file.listFiles()?.map { buildNode(it) }?.sortedBy { it.name } ?: emptyList()
        } else {
            null
        }
        return TreeNode(file.name, file.isDirectory, children)
    }
}

data class TreeNode(
    val name: String,
    val isDirectory: Boolean,
    val children: List<TreeNode>?
)
