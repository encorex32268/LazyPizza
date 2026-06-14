package com.lihan.lazypizza.core.data.mapper

import com.lihan.lazypizza.core.data.local.CartItemEntity
import com.lihan.lazypizza.core.data.local.CartItemToppingEntity
import com.lihan.lazypizza.core.data.local.CartItemWithToppingsDB
import com.lihan.lazypizza.core.domain.model.CartItem
import com.lihan.lazypizza.core.domain.model.CartItemTopping
import com.lihan.lazypizza.core.domain.model.CartItemWithToppings
import com.lihan.lazypizza.core.domain.model.Product
import com.lihan.lazypizza.core.domain.model.Topping
import java.util.UUID

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

fun CartItemWithToppingsDB.toDomain(): CartItemWithToppings {
    return CartItemWithToppings(
        cartItem = cartItem.toDomain(),
        toppings = toppings.map { it.toDomain() }
    )
}