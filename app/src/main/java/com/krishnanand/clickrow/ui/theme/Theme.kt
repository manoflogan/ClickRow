package com.krishnanand.clickrow.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
        primary = N0,
        primaryVariant = darkN0,
        secondary = darkN6,
        onSurface = N0
)

private val LightColorPalette = lightColors(
    primary = N7,
    primaryVariant = N0,
    secondary = N5,
    background = N0,
    onSurface = darkN3
)

@Composable
fun ClickRowTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
    )
}