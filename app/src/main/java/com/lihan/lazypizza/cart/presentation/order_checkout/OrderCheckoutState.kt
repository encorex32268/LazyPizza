package com.lihan.lazypizza.cart.presentation.order_checkout

import com.lihan.lazypizza.cart.presentation.model.CartItemWithToppingsUi
import com.lihan.lazypizza.menu.presentation.model.ProductUi

data class OrderCheckoutState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
    val isExpandDetail: Boolean = false
)