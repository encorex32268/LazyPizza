package com.lihan.lazypizza.core.data.mapper

import com.lihan.lazypizza.core.data.local.CartItemEntity
import com.lihan.lazypizza.core.data.local.CartItemToppingEntity
import com.lihan.lazypizza.core.data.local.CartItemWithToppingsCrossRef
import com.lihan.lazypizza.core.domain.model.CartItem
import com.lihan.lazypizza.core.domain.model.CartItemTopping
import com.lihan.lazypizza.core.domain.model.CartItemWithToppings

fun CartItemEntity.toDomain(): CartItem {
    return CartItem(
        cartItemId = cartItemId,
        id =id,
        productId = productId,
        quantity = quantity,
        name = name,
        price = price,
        imageUrl = imageUrl
    )
}

fun CartItem.toEntity(): CartItemEntity {
    return CartItemEntity(
        id =id,
        productId = productId,
        quantity = quantity,
        name = name,
        price = price,
        imageUrl = imageUrl
    )
}

fun CartItemToppingEntity.toDomain(): CartItemTopping {
    return CartItemTopping(
        cartItemId = cartItemId,
        toppingId = toppingId,
        quantity = quantity,
        price = price,
        name = name
    )
}

fun CartItemTopping.toEntity(): CartItemToppingEntity{
    return CartItemToppingEntity(
        cartItemId = cartItemId,
        toppingId = toppingId,
        quantity = quantity,
        name = name,
        price = price
    )
}

fun CartItemWithToppingsCrossRef.toDomain(): CartItemWithToppings {
    return CartItemWithToppings(
        cartItem = cartItem.toDomain(),
        toppings = toppings.map { it.toDomain() }
    )
}