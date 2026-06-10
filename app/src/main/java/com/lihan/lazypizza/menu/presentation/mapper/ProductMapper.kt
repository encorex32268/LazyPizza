package com.lihan.lazypizza.menu.presentation.mapper

import com.lihan.lazypizza.core.domain.model.Product
import com.lihan.lazypizza.menu.presentation.ProductType
import com.lihan.lazypizza.menu.presentation.model.ProductUi

fun Product.toUi(): ProductUi {
    return ProductUi(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        price = price,
        type = ProductType.fromString(category)
    )
}