package com.lihan.lazypizza.cart.presentation.model

data class CartItemToppingUi(
    val cartItemId: Long,
    val toppingId: String,
    val quantity: Int,
    val name: String,
    val price: Double
)
