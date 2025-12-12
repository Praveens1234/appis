package com.appis.android.domain.engine

import javax.inject.Inject

class PackageResolver @Inject constructor() {
    fun fixPackage(code: String, expectedPackage: String): String {
        val lines = code.lines().toMutableList()
        val packageLineIndex = lines.indexOfFirst { it.trim().startsWith("package ") }
        
        if (packageLineIndex != -1) {
            lines[packageLineIndex] = "package $expectedPackage"
        } else {
            lines.add(0, "package $expectedPackage")
        }
        return lines.joinToString("\n")
    }
}
