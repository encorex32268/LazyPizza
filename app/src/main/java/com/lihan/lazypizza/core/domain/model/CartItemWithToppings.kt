package com.lihan.lazypizza.core.domain.model

data class CartItemWithToppings(
    val cartItem: CartItem,
    val toppings: List<CartItemTopping>
)
