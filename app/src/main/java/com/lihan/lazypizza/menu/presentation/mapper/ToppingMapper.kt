package com.lihan.lazypizza.menu.presentation.mapper

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