package com.lihan.lazypizza.cart.presentation.model

data class CartItemUi(
    val cartItemId: Long,
    val id: Int,
    val productId: String,
    val quantity: Int,
    val name: String,
    val price: Double,
    val imageUrl: String
)
