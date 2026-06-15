package com.lihan.lazypizza.cart.presentation.model

import com.lihan.lazypizza.core.domain.formatToTwoDecimals


data class CartItemWithToppingsUi(
    val cartItem: CartItemUi,
    val toppings: List<CartItemToppingUi>
){
    private val oneItemPrice: Double
        get() {
            val cartItemPrice = cartItem.price
            val toppingsPrice = toppings.sumOf { it.price * it.quantity }

            return (cartItemPrice + toppingsPrice).formatToTwoDecimals().toDouble()
        }

    val totalPrice: String
        get() = "$${(oneItemPrice * cartItem.quantity).formatToTwoDecimals()}"

    val priceDetail: String
        get() = "${cartItem.quantity} x $${oneItemPrice.formatToTwoDecimals()}"

    val toppingsDescription: String
        get() = toppings.joinToString("\n") {
            "${it.quantity} x ${it.name}"
        }

}
