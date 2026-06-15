package com.lihan.lazypizza.core.domain.model

data class CartItem(
    val cartItemId: Long? = null,
    val id: Int,
    val productId: String,
    val quantity: Int,
    val name: String,
    val price: Double,
    val imageUrl: String
)
