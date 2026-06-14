package com.lihan.lazypizza.core.domain.model

data class CartItemTopping(
    val cartItemId: Long,
    val toppingId: String,
    val quantity: Int
)
