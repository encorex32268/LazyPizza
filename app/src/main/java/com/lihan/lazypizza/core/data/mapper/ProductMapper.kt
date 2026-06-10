package com.lihan.lazypizza.core.data.mapper

import com.lihan.lazypizza.core.data.local.ProductEntity
import com.lihan.lazypizza.core.data.model.ProductDto
import com.lihan.lazypizza.core.domain.model.Product

fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
        category = category,
        description = ingredients.joinToString(",")
    )
}

fun ProductDto.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
        category = category,
        description = ingredients.joinToString(",")
    )
}

fun ProductEntity.toDomain(): Product {
    return Product(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
        category = category,
        description = description
    )
}