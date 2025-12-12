package com.appis.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appis.android.domain.model.Project
import com.appis.android.domain.model.ProjectStatus
import com.appis.android.domain.orchestrator.SaasOrchestrator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateProjectViewModel @Inject constructor(
    private val orchestrator: SaasOrchestrator
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun createProject(title: String, description: String, onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            val project = Project(
                id = UUID.randomUUID().toString(),
                title = title,
                description = description,
                status = ProjectStatus.RUNNING
            )
            orchestrator.startPipeline(project)
            _isLoading.value = false
            onSuccess(project.id)
        }
    }
}
