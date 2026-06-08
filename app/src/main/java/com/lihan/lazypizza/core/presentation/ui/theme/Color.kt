package com.lihan.lazypizza.core.presentation.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

// Light Theme colors from design specs
val PrimaryLight = Color(0xFFF36B50)
val TextPrimaryLight = Color(0xFF03131F)
val TextSecondaryLight = Color(0xFF627686)
val TextOnPrimaryLight = Color(0xFFFFFFFF)
val BgLight = Color(0xFFFAFBFC)
val SurfaceHigherLight = Color(0xFFFFFFFF)
val SurfaceHighestLight = Color(0xFFF0F3F6)
val OutlineLight = Color(0xFFE6E7ED)
val SuccessLight = Color(0xFF2E7D32)
val WarningLight = Color(0xFFF9A825)

// Dark Theme colors (Material 3 cohesive design)
val PrimaryDark = Color(0xFFFF8F7B)
val TextPrimaryDark = Color(0xFFF3F6F8)
val TextSecondaryDark = Color(0xFF8CA0B0)
val TextOnPrimaryDark = Color(0xFF1E0A05)
val BgDark = Color(0xFF0C1319)
val SurfaceHigherDark = Color(0xFF172027)
val SurfaceHighestDark = Color(0xFF202D37)
val OutlineDark = Color(0xFF2E3B46)
val SuccessDark = Color(0xFF81C784)
val WarningDark = Color(0xFFFFE082)

// Extension to detect if ColorScheme is light
val ColorScheme.isLight: Boolean
    get() = this.background.luminance() > 0.5f

// Custom Colors as extensions on ColorScheme
val ColorScheme.success: Color
    get() = if (isLight) SuccessLight else SuccessDark

val ColorScheme.warning: Color
    get() = if (isLight) WarningLight else WarningDark

val ColorScheme.surfaceHigher: Color
    get() = if (isLight) SurfaceHigherLight else SurfaceHigherDark

val ColorScheme.surfaceHighest: Color
    get() = if (isLight) SurfaceHighestLight else SurfaceHighestDark

val ColorScheme.overlay: Color
    get() = if (isLight) Color(0xFF03131F).copy(alpha = 0.6f) else Color(0xFF000000).copy(alpha = 0.7f)

val ColorScheme.primaryGradient: Brush
    get() = if (isLight) {
        Brush.linearGradient(
            colors = listOf(Color(0xFFF9966F), Color(0xFFF36B50))
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color(0xFFFFAE9E), Color(0xFFFF8F7B))
        )
    }

// Opacity variants
val ColorScheme.textPrimary8: Color
    get() = onBackground.copy(alpha = 0.08f)

val ColorScheme.textSecondary8: Color
    get() = onSurfaceVariant.copy(alpha = 0.08f)

val ColorScheme.textOnPrimary12: Color
    get() = onPrimary.copy(alpha = 0.12f)

val ColorScheme.primary8: Color
    get() = primary.copy(alpha = 0.08f)

val ColorScheme.outline50: Color
    get() = outline.copy(alpha = 0.5f)