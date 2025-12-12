package com.appis.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appis.android.data.local.SettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsManager: SettingsManager
) : ViewModel() {

    private val _nvidiaKey = MutableStateFlow<String?>(null)
    val nvidiaKey = _nvidiaKey.asStateFlow()

    private val _githubToken = MutableStateFlow<String?>(null)
    val githubToken = _githubToken.asStateFlow()

    private val _githubUsername = MutableStateFlow<String?>(null)
    val githubUsername = _githubUsername.asStateFlow()

    init {
        loadKeys()
    }

    private fun loadKeys() {
        _nvidiaKey.value = settingsManager.getNvidiaKey()
        _githubToken.value = settingsManager.getGithubToken()
        _githubUsername.value = settingsManager.getGithubUsername()
    }

    fun saveKeys(nvidia: String, github: String, username: String) {
        settingsManager.saveNvidiaKey(nvidia)
        settingsManager.saveGithubToken(github)
        settingsManager.saveGithubUsername(username)
        loadKeys()
    }
}
