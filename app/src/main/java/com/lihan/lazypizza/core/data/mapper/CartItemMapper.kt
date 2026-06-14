package com.lihan.lazypizza.core.data.mapper

import com.lihan.lazypizza.core.data.local.CartItemEntity
import com.lihan.lazypizza.core.data.local.CartItemToppingEntity
import com.lihan.lazypizza.core.data.local.CartItemWithToppingsCrossRef
import com.lihan.lazypizza.core.domain.model.CartItem
import com.lihan.lazypizza.core.domain.model.CartItemTopping
import com.lihan.lazypizza.core.domain.model.CartItemWithToppings

fun CartItemEntity.toDomain(): CartItem {
    return CartItem(
        id =id,
        productId = productId,
        quantity = quantity
    )
}

fun CartItem.toEntity(): CartItemEntity {
    return CartItemEntity(
        id =id,
        productId = productId,
        quantity = quantity
    )
}

fun CartItemToppingEntity.toDomain(): CartItemTopping {
    return CartItemTopping(
        cartItemId = cartItemId,
        toppingId = toppingId,
        quantity = quantity
    )
}

fun CartItemTopping.toEntity(): CartItemToppingEntity{
    return CartItemToppingEntity(
        cartItemId = cartItemId,
        toppingId = toppingId,
        quantity = quantity
    )
}

fun CartItemWithToppingsCrossRef.toDomain(): CartItemWithToppings {
    return CartItemWithToppings(
        cartItem = cartItem.toDomain(),
        toppings = toppings.map { it.toDomain() }
    )
}