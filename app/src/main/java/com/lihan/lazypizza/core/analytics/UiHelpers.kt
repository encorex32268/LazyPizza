
package com.lihan.lazypizza.core.analytics

import androidx.compose.runtime.staticCompositionLocalOf


val LocalAnalyticsHelper = staticCompositionLocalOf<AnalyticsHelper> {
    NoOpAnalyticsHelper()
}
