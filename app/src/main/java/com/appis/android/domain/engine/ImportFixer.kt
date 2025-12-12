package com.appis.android.domain.engine

import javax.inject.Inject

class ImportFixer @Inject constructor() {
    private val commonImports = mapOf(
        "LazyColumn" to "androidx.compose.foundation.lazy.LazyColumn",
        "Text" to "androidx.compose.material3.Text",
        "Composable" to "androidx.compose.runtime.Composable",
        "Modifier" to "androidx.compose.ui.Modifier",
        "Inject" to "javax.inject.Inject",
        "ViewModel" to "androidx.lifecycle.ViewModel"
    )

    fun addMissingImports(code: String): String {
        val lines = code.lines().toMutableList()
        val existingImports = lines.filter { it.startsWith("import ") }.toSet()
        val packageLineIndex = lines.indexOfFirst { it.trim().startsWith("package ") }
        val insertIndex = if (packageLineIndex != -1) packageLineIndex + 1 else 0

        commonImports.forEach { (symbol, fullClass) ->
            if (code.contains(symbol) && !code.contains(fullClass)) {
                 if (!existingImports.any { it.contains(fullClass) }) {
                     lines.add(insertIndex + 1, "import $fullClass")
                 }
            }
        }
        return lines.joinToString("\n")
    }
}
