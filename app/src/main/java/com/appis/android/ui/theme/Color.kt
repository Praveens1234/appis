package com.appis.android.ui.theme

import androidx.compose.ui.graphics.Color

object AppisColors {
    // Backgrounds
    val BackgroundDeep = Color(0xFF0A0F1C) // Deep Navy/Charcoal
    val BackgroundGradientStart = Color(0xFF0F172A)
    val BackgroundGradientEnd = Color(0xFF1E1B4B)
    
    // Accents
    val NeonCyan = Color(0xFF00F0FF)
    val NeonPurple = Color(0xFFB026FF)
    val NeonBlue = Color(0xFF2979FF)
    val NeonGreen = Color(0xFF00FF9D)
    val NeonRed = Color(0xFFFF3D00)
    
    // Glass
    val GlassSurface = Color(0xFFFFFFFF).copy(alpha = 0.05f)
    val GlassWhite10 = Color(0xFFFFFFFF).copy(alpha = 0.10f)
    val GlassWhite20 = Color(0xFFFFFFFF).copy(alpha = 0.20f)
    val GlassBorder = Color(0xFFFFFFFF).copy(alpha = 0.15f)
    
    // Text
    val GlassTextPrimary = Color(0xFFFFFFFF)
    val GlassTextSecondary = Color(0xFF94A3B8)
    val TextGray = Color(0xFF94A3B8) // Alias for GlassTextSecondary
    
    // Status
    val StatusSuccess = NeonGreen
    val StatusWarning = Color(0xFFFFCC00)
    val StatusError = NeonRed
    val StatusRunning = NeonBlue
    val StatusPending = Color(0xFF64748B)
}
