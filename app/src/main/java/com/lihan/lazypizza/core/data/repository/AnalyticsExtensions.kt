package com.lihan.lazypizza.core.data.repository

import com.lihan.lazypizza.core.analytics.AnalyticsEvent
import com.lihan.lazypizza.core.analytics.AnalyticsHelper

fun AnalyticsHelper.logCreateOrder(result: String,data: Any? = null){
    logEvent(
        event = AnalyticsEvent(
            type = "create_order",
            extras = listOf(
                AnalyticsEvent.Param(key = "create_result", value = result),
                AnalyticsEvent.Param(key = "create_data", value = data)
            )
        )
    )
}

fun AnalyticsHelper.logUserPhoneNumber(phoneNumber: String) {
    logEvent(
        event = AnalyticsEvent(
            type = "set_user_phone_nubmer",
            extras = listOf(
                AnalyticsEvent.Param(key = "phoneNumber", value = phoneNumber),
            )
        )
    )
}

fun AnalyticsHelper.logUserId(userId: String) {
    logEvent(
        event = AnalyticsEvent(
            type = "set_user_id",
            extras = listOf(
                AnalyticsEvent.Param(key = "userId", value = userId),
            )
        )
    )
}

fun AnalyticsHelper.logOrderId(orderId: String) {
    logEvent(
        event = AnalyticsEvent(
            type = "set_order_id",
            extras = listOf(
                AnalyticsEvent.Param(key = "orderId", value = orderId),
            )
        )
    )
}