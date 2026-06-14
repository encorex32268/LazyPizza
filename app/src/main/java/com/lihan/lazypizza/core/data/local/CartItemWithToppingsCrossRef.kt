package com.lihan.lazypizza.core.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class CartItemWithToppingsCrossRef(
    @Embedded
    val cartItem: CartItemEntity,

    @Relation(
        parentColumn = "cartItemId",
        entityColumn = "cartItemId"
    )
    val toppings: List<CartItemToppingEntity>

)
