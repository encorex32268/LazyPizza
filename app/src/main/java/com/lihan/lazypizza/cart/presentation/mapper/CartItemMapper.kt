package com.lihan.lazypizza.cart.presentation.mapper

import com.lihan.lazypizza.cart.presentation.model.CartItemToppingUi
import com.lihan.lazypizza.cart.presentation.model.CartItemUi
import com.lihan.lazypizza.cart.presentation.model.CartItemWithToppingsUi
import com.lihan.lazypizza.core.domain.model.CartItem
import com.lihan.lazypizza.core.domain.model.CartItemTopping
import com.lihan.lazypizza.core.domain.model.CartItemWithToppings


fun CartItem.toUi(): CartItemUi? {
    if (cartItemId == null) return null
    return CartItemUi(
        cartItemId = cartItemId,
        id = id,
        productId = productId,
        quantity = quantity,
        name = name,
        price = price,
        imageUrl = imageUrl
    )
}

fun CartItemTopping.toUi(): CartItemToppingUi {
    return CartItemToppingUi(
        cartItemId = cartItemId,
        toppingId = toppingId,
        quantity = quantity,
        name = name,
        price = price
    )
}

fun CartItemWithToppings.toUi(): CartItemWithToppingsUi?{
    val toUiResult = cartItem.toUi() ?: return null
    return CartItemWithToppingsUi(
        cartItem = toUiResult,
        toppings = toppings.map { it.toUi() }
    )
}