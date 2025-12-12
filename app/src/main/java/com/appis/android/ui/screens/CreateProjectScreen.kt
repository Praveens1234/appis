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
import com.appis.android.ui.viewmodel.CreateProjectViewModel

@Composable
fun CreateProjectScreen(
    onProjectCreated: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: CreateProjectViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        GlassCard(modifier = Modifier.padding(24.dp)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Create New Project", style = AppisTypography.headlineMedium)
                
                GlassInput(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = "Project Title",
                    modifier = Modifier.fillMaxWidth()
                )
                
                GlassInput(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = "Description (e.g. A crypto wallet app)",
                    modifier = Modifier.fillMaxWidth()
                )

                NeonButton(
                    text = if (isLoading) "Creating..." else "Start Building",
                    onClick = {
                        viewModel.createProject(title, description) { projectId ->
                            onProjectCreated(projectId)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading && title.isNotBlank() && description.isNotBlank()
                )
            }
        }
    }
}
