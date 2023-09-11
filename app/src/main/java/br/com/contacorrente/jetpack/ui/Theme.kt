package br.com.contacorrente.jetpack.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun ApplicationMainTheme(
    darkTheme: Boolean = !isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) darkColorPalette else lightColorPalette

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}


private val darkColorPalette = darkColorScheme(
    primary = Purple200,

    secondary = Teal200
)

private val lightColorPalette = lightColorScheme(
    primary = MediumBlack,

    secondary = Teal200
)