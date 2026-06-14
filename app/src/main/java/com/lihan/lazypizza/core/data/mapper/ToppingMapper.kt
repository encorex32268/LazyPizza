package com.lihan.lazypizza.core.data.mapper

import com.lihan.lazypizza.core.data.local.CartItemToppingEntity
import com.lihan.lazypizza.core.data.local.ProductEntity
import com.lihan.lazypizza.core.data.local.ToppingEntity
import com.lihan.lazypizza.core.data.model.ProductDto
import com.lihan.lazypizza.core.data.model.ToppingDto
import com.lihan.lazypizza.core.domain.model.Topping

fun ToppingDto.toDomain(): Topping {
    return Topping(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl
    )
}

fun ToppingDto.toEntity(): ToppingEntity {
    return ToppingEntity(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl
    )
}

fun ToppingEntity.toDomain(): Topping {
    return Topping(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl
    )
}

fun Topping.toEntity(
    quantity: Int
): CartItemToppingEntity {

    return CartItemToppingEntity(
        cartItemId = 0,
        id = 0,
        toppingId = id,
        quantity = quantity,
    )
}