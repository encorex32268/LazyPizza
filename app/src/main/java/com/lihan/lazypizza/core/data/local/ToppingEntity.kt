package com.lihan.lazypizza.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToppingEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String
)
