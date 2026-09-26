package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = JapanRed,
    onPrimary = Color.White,
    primaryContainer = RawJapanRedLight,
    onPrimaryContainer = JapanRedDark,
    secondary = RawSlate700,
    onSecondary = Color.White,
    secondaryContainer = RawSurfaceSubtle,
    onSecondaryContainer = RawSlate900,
    tertiary = Amber600,
    onTertiary = Color.White,
    tertiaryContainer = RawAmber50,
    onTertiaryContainer = Amber600,
    background = RawLightBg,
    onBackground = RawSlate900,
    surface = RawCardBg,
    onSurface = RawSlate900,
    surfaceVariant = RawSurfaceSubtle,
    onSurfaceVariant = RawSlate700,
    outline = RawBorderSubtle
)

val DarkColorScheme = darkColorScheme(
    primary = JapanRed,
    onPrimary = Color.White,
    primaryContainer = RawDarkRedContainer,
    onPrimaryContainer = Color(0xFFFFD1D9),
    secondary = RawDarkTextMuted,
    onSecondary = RawDarkBg,
    secondaryContainer = RawDarkSurface,
    onSecondaryContainer = Color.White,
    tertiary = Amber600,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF451A03),
    onTertiaryContainer = RawAmber50,
    background = RawDarkBg,
    onBackground = RawDarkTextPrimary,
    surface = RawDarkSurface,
    onSurface = RawDarkTextPrimary,
    surfaceVariant = RawDarkSurfaceSubtle,
    onSurfaceVariant = RawDarkTextSecondary,
    outline = RawDarkBorder
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
