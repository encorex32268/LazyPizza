package com.lihan.lazypizza.menu.presentation.mapper

import com.lihan.lazypizza.core.domain.model.CartItem
import com.lihan.lazypizza.core.domain.model.Product
import com.lihan.lazypizza.menu.presentation.ProductType
import com.lihan.lazypizza.menu.presentation.ProductType.Companion.toTypeName
import com.lihan.lazypizza.menu.presentation.model.ProductUi

fun Product.toUi(): ProductUi {
    return ProductUi(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        price = price,
        category = category
    )
}

fun ProductUi.toDomain(): Product {
    return Product(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        price = price,
        category = category
    )
}

fun ProductUi.toCartItem(createdId: Int): CartItem {
    return CartItem(
        cartItemId = null,
        id = createdId,
        productId = id,
        quantity = 1
    )
}