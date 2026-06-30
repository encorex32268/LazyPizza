package com.lihan.lazypizza.core.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = TextOnPrimaryDark,
    background = BgDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceHigherDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceHighestDark,
    onSurfaceVariant = TextSecondaryDark,
    outline = OutlineDark,
    secondary = TextSecondaryDark,
    onSecondary = TextPrimaryDark,
    tertiary = SuccessDark,
    onTertiary = TextPrimaryDark
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = TextOnPrimaryLight,
    background = BgLight,
    onBackground = TextPrimaryLight,
    surface = SurfaceHigherLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = SurfaceHighestLight,
    onSurfaceVariant = TextSecondaryLight,
    outline = OutlineLight,
    secondary = TextSecondaryLight,
    onSecondary = TextOnPrimaryLight,
    tertiary = SuccessLight,
    onTertiary = TextOnPrimaryLight
)

@Composable
fun LazyPizzaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set default to false to prioritize custom themed colors
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialExpressiveTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}