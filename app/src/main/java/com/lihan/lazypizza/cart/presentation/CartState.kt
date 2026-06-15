package com.lihan.lazypizza.cart.presentation

import com.lihan.lazypizza.cart.presentation.model.CartItemWithToppingsUi

data class CartState(
    val isLoading: Boolean = false,
    val items: List<CartItemWithToppingsUi> = emptyList()
)