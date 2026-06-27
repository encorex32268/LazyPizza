package com.lihan.lazypizza.history.presentation.mapper

import com.lihan.lazypizza.core.domain.model.OrderHistory
import com.lihan.lazypizza.history.presentation.model.OrderHistoryUi
import com.lihan.lazypizza.history.presentation.model.OrderStatus.Companion.toOrderStatus

fun OrderHistory.toUi(): OrderHistoryUi {
    return OrderHistoryUi(
        createAt = createAt,
        status = status.toOrderStatus(),
        totalAmount = totalAmount.toString(),
        details = details.joinToString("\n")
    )
}