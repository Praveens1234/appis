package com.appis.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appis.android.ui.components.GlassCard
import com.appis.android.ui.components.GlassInput
import com.appis.android.ui.components.NeonButton
import com.appis.android.ui.theme.AppisTypography
import com.appis.android.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    var nvidiaKey by remember { mutableStateOf("") }
    var githubToken by remember { mutableStateOf("") }
    var githubUsername by remember { mutableStateOf("") }
    val savedNvidia by viewModel.nvidiaKey.collectAsState()
    val savedGithub by viewModel.githubToken.collectAsState()
    val savedUsername by viewModel.githubUsername.collectAsState()

    // Initialize with saved values
    LaunchedEffect(savedNvidia) {
        if (savedNvidia != null) nvidiaKey = savedNvidia!!
    }
    LaunchedEffect(savedGithub) {
        if (savedGithub != null) githubToken = savedGithub!!
    }
    LaunchedEffect(savedUsername) {
        if (savedUsername != null) githubUsername = savedUsername!!
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        GlassCard(modifier = Modifier.padding(24.dp)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Settings", style = AppisTypography.headlineMedium)
                
                GlassInput(
                    value = nvidiaKey,
                    onValueChange = { nvidiaKey = it },
                    placeholder = "Nvidia API Key",
                    modifier = Modifier.fillMaxWidth()
                )
                
                GlassInput(
                    value = githubToken,
                    onValueChange = { githubToken = it },
                    placeholder = "GitHub Token",
                    modifier = Modifier.fillMaxWidth()
                )
                
                GlassInput(
                    value = githubUsername,
                    onValueChange = { githubUsername = it },
                    placeholder = "GitHub Username",
                    modifier = Modifier.fillMaxWidth()
                )

                NeonButton(
                    text = "Save",
                    onClick = {
                        viewModel.saveKeys(nvidiaKey, githubToken, githubUsername)
                        onBack()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
