package com.lihan.lazypizza.cart.presentation.order_checkout

sealed interface OrderCheckoutAction {
    data object OnExpandClick: OrderCheckoutAction
}