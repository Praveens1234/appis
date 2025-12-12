package com.appis.android.domain.orchestrator.stages

import com.appis.android.data.git.RepoManager
import com.appis.android.domain.orchestrator.EventBus
import com.appis.android.domain.orchestrator.PipelineEvent
import kotlinx.coroutines.delay
import javax.inject.Inject

class BuildStage @Inject constructor(
    private val repoManager: RepoManager,
    private val eventBus: EventBus
) {
    suspend fun execute(token: String, owner: String, repoName: String) {
        // Get latest run before trigger to identify the new one
        val previousRun = repoManager.getLatestWorkflowRun(token, owner, repoName)
        val previousRunId = previousRun?.id ?: 0L

        // Trigger workflow
        val triggered = repoManager.triggerWorkflow(token, owner, repoName, "android_build.yml")
        if (!triggered) {
             eventBus.emit(PipelineEvent.Error("Failed to trigger GitHub Action"))
             return
        }

        // Poll for result
        repeat(20) { // Poll for up to 100 seconds
            delay(5000)
            val latestRun = repoManager.getLatestWorkflowRun(token, owner, repoName)
            
            if (latestRun != null) {
                // Check if this is a new run
                if (latestRun.id > previousRunId) {
                    when (latestRun.conclusion) {
                        "success" -> {
                            val artifactUrl = repoManager.getArtifactUrl(token, owner, repoName, latestRun.id)
                            eventBus.emit(PipelineEvent.BuildFinished(true, artifactUrl))
                            return
                        }
                        "failure" -> {
                            val logs = repoManager.getWorkflowLogs(token, owner, repoName, latestRun.id)
                            eventBus.emit(PipelineEvent.BuildFailed(logs))
                            return
                        }
                        else -> {
                            // Still running or queued
                        }
                    }
                }
            }
        }
        eventBus.emit(PipelineEvent.Error("Build timed out"))
    }
}
