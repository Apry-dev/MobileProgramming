package com.example.cataasapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = CatOrange,
    secondary = CatGray,
    tertiary = CatBrown,
    background = Color(0xFF1E1E1E),
    surface = Color(0xFF2E2E2E),
    onPrimary = CatWhite,
    onSecondary = CatWhite,
    onBackground = CatWhite,
    onSurface = CatWhite
)

private val LightColorScheme = lightColorScheme(
    primary = CatOrange,
    secondary = CatGray,
    tertiary = CatBrown,
    background = CatWhite,
    surface = CatWhite,
    onPrimary = CatWhite,
    onSecondary = CatWhite,
    onBackground = Color(0xFF1E1E1E),
    onSurface = Color(0xFF1E1E1E)
)

@Composable
fun CataasappTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && true -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}