package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = JapanRed,
    onPrimary = Color.White,
    primaryContainer = JapanRedLight,
    onPrimaryContainer = JapanRedDark,
    secondary = Slate700,
    onSecondary = Color.White,
    secondaryContainer = SurfaceSubtle,
    onSecondaryContainer = Slate900,
    tertiary = Amber600,
    onTertiary = Color.White,
    tertiaryContainer = Amber50,
    onTertiaryContainer = Amber600,
    background = LightBg,
    onBackground = Slate900,
    surface = CardBg,
    onSurface = Slate900,
    surfaceVariant = SurfaceSubtle,
    onSurfaceVariant = Slate700,
    outline = BorderSubtle
)

private val DarkColorScheme = darkColorScheme(
    primary = JapanRed,
    onPrimary = Color.White,
    primaryContainer = JapanRedDark,
    onPrimaryContainer = JapanRedLight,
    secondary = Slate400,
    onSecondary = Slate900,
    secondaryContainer = Slate700,
    onSecondaryContainer = Color.White,
    tertiary = Amber600,
    onTertiary = Color.White,
    tertiaryContainer = Amber50,
    onTertiaryContainer = Amber600,
    background = Color(0xFF0F172A),
    onBackground = Color(0xFFF8FAFC),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF475569)
)

@Composable
fun JapaneseBanglaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
