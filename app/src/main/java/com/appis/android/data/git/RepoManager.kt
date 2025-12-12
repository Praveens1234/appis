package com.appis.android.data.git

import com.appis.android.data.remote.GitHubService
import javax.inject.Inject

class RepoManager @Inject constructor(
    private val gitHubService: GitHubService
) {
    suspend fun createRepo(token: String, name: String, description: String, private: Boolean = false): Boolean {
        try {
            val response = gitHubService.createRepo(
                com.appis.android.data.remote.GitHubRepoRequest(name, description, private)
            )
            return response.isSuccessful
        } catch (e: Exception) {
            return false
        }
    }

    // Advanced: Push multiple files as a single atomic commit
    suspend fun pushChanges(token: String, owner: String, repo: String, files: Map<String, String>, message: String): Boolean {
        try {
            // 1. Get reference to HEAD (main)
            val refResponse = gitHubService.getRef(owner, repo, "heads/main")
            if (!refResponse.isSuccessful) return false
            val baseTreeSha = refResponse.body()?.`object`?.sha ?: return false

            // 2. Create Blobs for each file
            val treeItems = mutableListOf<com.appis.android.data.remote.TreeItem>()
            for ((path, content) in files) {
                val blobResponse = gitHubService.createBlob(owner, repo, com.appis.android.data.remote.CreateBlobRequest(content))
                if (blobResponse.isSuccessful) {
                    val blobSha = blobResponse.body()?.sha ?: continue
                    treeItems.add(com.appis.android.data.remote.TreeItem(path, "100644", "blob", blobSha))
                }
            }

            // 3. Create Tree
            val treeResponse = gitHubService.createTree(owner, repo, com.appis.android.data.remote.CreateTreeRequest(baseTreeSha, treeItems))
            if (!treeResponse.isSuccessful) return false
            val newTreeSha = treeResponse.body()?.sha ?: return false

            // 4. Create Commit
            val commitResponse = gitHubService.createCommit(owner, repo, com.appis.android.data.remote.CreateCommitRequest(message, newTreeSha, listOf(baseTreeSha)))
            if (!commitResponse.isSuccessful) return false
            val newCommitSha = commitResponse.body()?.sha ?: return false

            // 5. Update Ref
            val updateRefResponse = gitHubService.updateRef(owner, repo, "heads/main", com.appis.android.data.remote.UpdateRefRequest(newCommitSha))
            return updateRefResponse.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    suspend fun triggerWorkflow(token: String, owner: String, repo: String, workflowId: String, ref: String = "main"): Boolean {
        try {
             val response = gitHubService.triggerWorkflow(
                 owner,
                 repo,
                 workflowId,
                 com.appis.android.data.remote.WorkflowDispatchRequest(ref)
             )
             return response.isSuccessful
        } catch (e: Exception) {
            return false
        }
    }

    suspend fun getLatestWorkflowRun(token: String, owner: String, repo: String): com.appis.android.data.remote.WorkflowRun? {
        try {
            val response = gitHubService.listWorkflowRuns(owner, repo)
            return response.body()?.workflow_runs?.firstOrNull()
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun getWorkflowLogs(token: String, owner: String, repo: String, runId: Long): String {
        try {
            val response = gitHubService.getRunLogs(owner, repo, runId)
            return response.body()?.string() ?: "No logs found"
        } catch (e: Exception) {
            return "Error fetching logs: ${e.message}"
        }
    }

    suspend fun getArtifactUrl(token: String, owner: String, repo: String, runId: Long): String? {
        try {
            val response = gitHubService.listRunArtifacts(owner, repo, runId)
            val artifacts = response.body()?.artifacts
            // Return download URL of first artifact (usually the APK zip)
            return artifacts?.firstOrNull()?.archive_download_url
        } catch (e: Exception) {
            return null
        }
    }
}
