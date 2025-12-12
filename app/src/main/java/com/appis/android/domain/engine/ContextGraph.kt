package com.appis.android.domain.engine

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContextGraph @Inject constructor() {
    private val dependencyMap = mutableMapOf<String, MutableSet<String>>()

    fun addDependency(file: String, dependsOn: String) {
        dependencyMap.getOrPut(file) { mutableSetOf() }.add(dependsOn)
    }

    fun getDependencies(file: String): Set<String> {
        return dependencyMap[file] ?: emptySet()
    }
    
    fun clear() {
        dependencyMap.clear()
    }
}
