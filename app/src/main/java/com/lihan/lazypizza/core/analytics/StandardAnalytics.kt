package com.lihan.lazypizza.core.analytics

import android.util.Log

class StandardAnalytics: AnalyticsHelper {

    companion object{
        private const val TAG = "StandardAnalytics"
    }

    override fun logEvent(event: AnalyticsEvent) {
        Log.d(TAG, "Received analytics event: $event")
    }
}