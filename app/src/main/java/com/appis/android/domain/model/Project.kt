package com.appis.android.domain.model

data class Project(
    val id: String,
    val title: String,
    val description: String,
    val status: ProjectStatus = ProjectStatus.CREATED,
    val lastUpdated: Long = System.currentTimeMillis()
)

enum class ProjectStatus {
    CREATED,
    RUNNING,
    SUCCESS,
    FAILED,
    HEALING
}
