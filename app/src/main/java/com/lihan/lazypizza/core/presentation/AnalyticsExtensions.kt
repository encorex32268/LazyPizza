package com.lihan.lazypizza.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.lihan.lazypizza.core.analytics.AnalyticsEvent
import com.lihan.lazypizza.core.analytics.AnalyticsHelper
import com.lihan.lazypizza.core.analytics.LocalAnalyticsHelper

fun AnalyticsHelper.logScreenView(screenName: String){
    logEvent(
        event = AnalyticsEvent(
            type = AnalyticsEvent.Types.SCREEN_VIEW,
            extras = listOf(
                AnalyticsEvent.Param(key = "screen_name", value = screenName),
            )
        )
    )
}

@Composable
fun TrackScreenViewEvent(
    screenName: String,
    analyticsHelper: AnalyticsHelper = LocalAnalyticsHelper.current,
) = DisposableEffect(Unit) {
    analyticsHelper.logScreenView(screenName)
    onDispose {}
}

