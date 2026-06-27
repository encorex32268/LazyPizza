package com.lihan.lazypizza.core.data.mapper

import com.lihan.lazypizza.core.data.local.OrderHistoryEntity
import com.lihan.lazypizza.core.data.model.OrderDto
import com.lihan.lazypizza.core.domain.model.OrderHistory

fun OrderHistoryEntity.toDomain(): OrderHistory{
    return OrderHistory(
        id = id,
        createAt = createAt,
        totalAmount = totalAmount,
        orderNumber = createAt.toString().takeLast(5).toLong(),
        status = status,
        details = details.split(",")
    )
}

fun OrderDto.toEntity(): OrderHistoryEntity {
    return OrderHistoryEntity(
        id = id,
        userId = userId,
        createAt = createAt,
        totalAmount = totalAmount,
        orderNumber = createAt.toString().takeLast(5).toLong(),
        status = status,
        details = details.joinToString(",")
    )
}