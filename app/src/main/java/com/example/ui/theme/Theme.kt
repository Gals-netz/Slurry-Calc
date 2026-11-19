package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SlurryLightColorScheme = lightColorScheme(
    primary = Blue600,
    onPrimary = Color.White,
    primaryContainer = Blue50,
    onPrimaryContainer = Blue900,
    secondary = Amber500,
    onSecondary = Slate950,
    secondaryContainer = Amber100,
    onSecondaryContainer = Amber950,
    tertiary = Emerald600,
    onTertiary = Color.White,
    tertiaryContainer = Emerald50,
    onTertiaryContainer = Emerald950,
    background = Slate100,
    onBackground = Slate800,
    surface = Color.White,
    onSurface = Slate900,
    surfaceVariant = Slate50,
    onSurfaceVariant = Slate600,
    outline = Slate300,
    outlineVariant = Slate200,
    error = Red600,
    onError = Color.White,
    errorContainer = Red50,
    onErrorContainer = Red800
)

@Composable
fun CekSedimenTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SlurryLightColorScheme,
        typography = Typography,
        content = content
    )
}
