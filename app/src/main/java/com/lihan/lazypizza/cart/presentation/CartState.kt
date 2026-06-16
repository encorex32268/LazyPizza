package com.lihan.lazypizza.cart.presentation

import com.lihan.lazypizza.cart.presentation.model.CartItemWithToppingsUi
import com.lihan.lazypizza.core.domain.formatToTwoDecimals
import com.lihan.lazypizza.menu.presentation.model.ProductUi

data class CartState(
    val isLoading: Boolean = false,
    val items: List<CartItemWithToppingsUi> = emptyList(),
    val recommendItems: List<ProductUi> = emptyList()
){

    val cartItemTotalPrice: String
        get() = items.sumOf {
            //remove $ symbol
            it.totalPrice.replace("$","").toDoubleOrNull() ?: 0.0
        }.formatToTwoDecimals()

}