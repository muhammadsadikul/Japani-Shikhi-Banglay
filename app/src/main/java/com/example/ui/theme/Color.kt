package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Raw Color Values for Light Palette
val RawJapanRed = Color(0xFFE11D48)
val RawJapanRedDark = Color(0xFFBE123C)
val RawJapanRedLight = Color(0xFFFFF1F2)
val RawJapanRedBorder = Color(0xFFFECDD3)

val RawLightBg = Color(0xFFF8FAFC)
val RawCardBg = Color(0xFFFFFFFF)
val RawSurfaceSubtle = Color(0xFFF1F5F9)
val RawBorderSubtle = Color(0xFFE2E8F0)

val RawSlate900 = Color(0xFF0F172A)
val RawSlate700 = Color(0xFF334155)
val RawSlate500 = Color(0xFF64748B)
val RawSlate400 = Color(0xFF94A3B8)
val RawAmber50 = Color(0xFFFFFBEB)

// Raw Color Values for Dark Palette
val RawDarkBg = Color(0xFF0F172A)
val RawDarkSurface = Color(0xFF1E293B)
val RawDarkSurfaceSubtle = Color(0xFF243248)
val RawDarkBorder = Color(0xFF334155)
val RawDarkTextPrimary = Color(0xFFF8FAFC)
val RawDarkTextSecondary = Color(0xFFCBD5E1)
val RawDarkTextMuted = Color(0xFF94A3B8)
val RawDarkRedContainer = Color(0xFF3B0B14)
val RawDarkRedBorder = Color(0xFF881337)

// Static Brand & Accent Colors
val JapanRed = RawJapanRed
val JapanRedDark = RawJapanRedDark
val Emerald600 = Color(0xFF059669)
val Amber600 = Color(0xFFD97706)
val Indigo600 = Color(0xFF4F46E5)

// Dynamic Theme-Aware Colors via Composable Accessors
val LightBg: Color
    @Composable get() = MaterialTheme.colorScheme.background

val CardBg: Color
    @Composable get() = MaterialTheme.colorScheme.surface

val SurfaceSubtle: Color
    @Composable get() = MaterialTheme.colorScheme.surfaceVariant

val BorderSubtle: Color
    @Composable get() = MaterialTheme.colorScheme.outline

val Slate900: Color
    @Composable get() = MaterialTheme.colorScheme.onSurface

val Slate700: Color
    @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

val Slate500: Color
    @Composable get() = if (MaterialTheme.colorScheme.background == RawDarkBg) RawDarkTextMuted else RawSlate500

val Slate400: Color
    @Composable get() = if (MaterialTheme.colorScheme.background == RawDarkBg) Color(0xFF64748B) else RawSlate400

val JapanRedLight: Color
    @Composable get() = MaterialTheme.colorScheme.primaryContainer

val JapanRedBorder: Color
    @Composable get() = if (MaterialTheme.colorScheme.background == RawDarkBg) RawDarkRedBorder else RawJapanRedBorder

val Emerald50: Color
    @Composable get() = if (MaterialTheme.colorScheme.background == RawDarkBg) Color(0xFF064E3B) else Color(0xFFECFDF5)

val Amber50: Color
    @Composable get() = if (MaterialTheme.colorScheme.background == RawDarkBg) Color(0xFF451A03) else Color(0xFFFFFBEB)

val Indigo50: Color
    @Composable get() = if (MaterialTheme.colorScheme.background == RawDarkBg) Color(0xFF1E1B4B) else Color(0xFFEEF2FF)
