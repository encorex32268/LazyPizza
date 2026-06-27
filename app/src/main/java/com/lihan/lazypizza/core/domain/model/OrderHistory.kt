package com.lihan.lazypizza.core.domain.model


data class OrderHistory(
    val id: String?=null,
    val userId: String?=null,
    val createAt: Long,
    val totalAmount: Double,
    val orderNumber: Long,
    val status: Int,
    val details: List<String>
)