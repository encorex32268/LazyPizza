package com.lihan.lazypizza.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    val id: String = "",
    val userId: String = "",
    val createAt: Long = 0,
    val totalAmount: Double = 0.0,
    val orderNumber: Long = 0,
    val status: Int = 0,
    val details: List<String> = emptyList()
)
