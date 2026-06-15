package com.lihan.lazypizza.menu.presentation.mapper

import com.lihan.lazypizza.core.domain.model.CartItemTopping
import com.lihan.lazypizza.core.domain.model.Topping
import com.lihan.lazypizza.menu.presentation.model.ToppingUi

fun Topping.toUi(): ToppingUi {
    return ToppingUi(
        id = id,
        name = name,
        imageUrl = imageUrl,
        price =  price
    )
}

fun ToppingUi.toCartItemTopping(): CartItemTopping{
    return CartItemTopping(
        cartItemId = 0,
        toppingId = id,
        quantity = count,
        name = name,
        price =  price
    )
}