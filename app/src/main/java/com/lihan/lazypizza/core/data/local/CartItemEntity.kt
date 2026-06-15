package com.lihan.lazypizza.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CartItemEntity(
    @PrimaryKey(autoGenerate = false)
    val cartItemId: Long?=null,
    val id: Int,
    val productId: String,
    val quantity: Int,
    val name: String,
    val price: Double,
    val imageUrl: String,
)
