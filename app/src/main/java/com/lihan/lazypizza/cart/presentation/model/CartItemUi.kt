package com.lihan.lazypizza.cart.presentation.model

import com.lihan.lazypizza.menu.presentation.ProductType

data class CartItemUi(
    val cartItemId: Long,
    val id: Int,
    val productId: String,
    val quantity: Int,
    val name: String,
    val price: Double,
    val imageUrl: String
){
    val productType: ProductType
        get() = if (productId.startsWith("pizza_")) ProductType.Pizza else ProductType.Other
}
