package com.lihan.lazypizza.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val category: String = "",
    val imageUrl: String = "",
    val ingredients: List<String> = emptyList()
)
