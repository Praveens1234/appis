package com.appis.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appis.android.ui.AppisViewModel
import com.appis.android.ui.screens.ChatScreen
import com.appis.android.ui.screens.CreateProjectScreen
import com.appis.android.ui.screens.DashboardScreen
import com.appis.android.ui.screens.SettingsScreen
import com.appis.android.ui.screens.BuildStatusScreen
import com.appis.android.ui.screens.code.CodeViewerScreen

object Routes {
    const val DASHBOARD = "dashboard"
    const val CREATE_PROJECT = "create_project"
    const val CHAT = "chat"
    const val SETTINGS = "settings"
    const val BUILD_STATUS = "build_status"
    const val CODE_VIEWER = "code_viewer"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Using Hilt ViewModels directly in screens is better for decoupling
    // but AppisViewModel seems to be the main shared VM in the old code.
    // The previous steps introduced specific ViewModels for screens.
    // We should respect that if possible, but the old AppNavigation code uses AppisViewModel.
    // Given the context, I will stick to what the screens expect.
    // Note: CreateProjectScreen signature in previous step was (String)->Unit, onBack.
    // ChatScreen signature was onBack.
    
    NavHost(navController = navController, startDestination = Routes.DASHBOARD) {
        
        composable(Routes.DASHBOARD) {
            DashboardScreen(
                onCreateProject = { navController.navigate(Routes.CREATE_PROJECT) },
                onViewExisting = { navController.navigate(Routes.BUILD_STATUS) },
                onSettings = { navController.navigate(Routes.SETTINGS) }
            )
        }

        composable(Routes.CREATE_PROJECT) {
            CreateProjectScreen(
                onProjectCreated = { projectId ->
                    // Navigate to chat
                    navController.navigate(Routes.CHAT)
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.CHAT) {
            ChatScreen(
                onBack = { navController.popBackStack() },
                onViewCode = { navController.navigate(Routes.CODE_VIEWER) }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.BUILD_STATUS) {
            BuildStatusScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.CODE_VIEWER) {
            // In a real app, we would pass file path as argument or use a shared ViewModel state
            // For now, let's show a placeholder or try to get content if possible.
            // Ideally, ChatViewModel or a shared CodeViewModel should hold the selected file content.
            CodeViewerScreen(
                fileName = "MainActivity.kt", // Placeholder until we add file selection
                code = "// Generated code will appear here after build.",
                onBack = { navController.popBackStack() }
            )
        }
    }
}
