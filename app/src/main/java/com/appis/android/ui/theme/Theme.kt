package com.appis.android.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = AppisColors.NeonCyan,
    secondary = AppisColors.NeonPurple,
    tertiary = AppisColors.NeonBlue,
    background = AppisColors.BackgroundDeep,
    surface = AppisColors.GlassSurface,
    onPrimary = AppisColors.BackgroundDeep,
    onSecondary = AppisColors.BackgroundDeep,
    onTertiary = AppisColors.GlassTextPrimary,
    onBackground = AppisColors.GlassTextPrimary,
    onSurface = AppisColors.GlassTextPrimary,
)

@Composable
fun AppisTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Always dark in Appis
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme // Force Dark Theme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = AppisColors.BackgroundDeep.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppisTypography,
        content = content
    )
}
