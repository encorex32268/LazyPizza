package com.lihan.lazypizza.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ToppingDto(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val imageUrl: String = ""
)
