package com.appis.android.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepoRequest(
    val name: String,
    val description: String,
    val private: Boolean = true,
    val auto_init: Boolean = true
)

@Serializable
data class GitHubRepoResponse(
    val id: Long,
    val name: String,
    val full_name: String,
    val html_url: String,
    val clone_url: String,
    val default_branch: String
)

@Serializable
data class GitHubContentRequest(
    val message: String,
    val content: String, // Base64 encoded
    val sha: String? = null,
    val branch: String = "main"
)

@Serializable
data class GitHubContentResponse(
    val content: GitHubFileContent?
)

@Serializable
data class GitHubFileContent(
    val name: String,
    val path: String,
    val sha: String
)

@Serializable
data class WorkflowDispatchRequest(
    val ref: String = "main"
)

@Serializable
data class WorkflowRun(
    val id: Long,
    val name: String,
    val status: String,
    val conclusion: String?,
    val created_at: String,
    val updated_at: String
)

@Serializable
data class WorkflowRunsResponse(
    val total_count: Int,
    val workflow_runs: List<WorkflowRun>
)

@Serializable
data class ArtifactsResponse(
    val total_count: Int,
    val artifacts: List<Artifact>
)

@Serializable
data class Artifact(
    val id: Long,
    val name: String,
    val size_in_bytes: Long,
    val url: String,
    val archive_download_url: String,
    val expired: Boolean,
    val created_at: String,
    val expires_at: String
)

// --- Git Data API Models ---

data class GitRefResponse(
    val ref: String,
    val node_id: String,
    val url: String,
    val `object`: GitObject
)

data class GitObject(
    val sha: String,
    val type: String,
    val url: String
)

data class CreateBlobRequest(
    val content: String,
    val encoding: String = "utf-8"
)

data class CreateBlobResponse(
    val sha: String,
    val url: String
)

data class CreateTreeRequest(
    val base_tree: String,
    val tree: List<TreeItem>
)

data class TreeItem(
    val path: String,
    val mode: String, // "100644" for file
    val type: String, // "blob"
    val sha: String
)

data class CreateTreeResponse(
    val sha: String,
    val url: String
)

data class CreateCommitRequest(
    val message: String,
    val tree: String,
    val parents: List<String>
)

data class CreateCommitResponse(
    val sha: String,
    val url: String,
    val author: CommitAuthor
)

data class CommitAuthor(
    val name: String,
    val email: String,
    val date: String
)

data class UpdateRefRequest(
    val sha: String,
    val force: Boolean = true
)
