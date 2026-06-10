package com.lihan.lazypizza.core.domain.model

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val description: String,
    val category: String
)
