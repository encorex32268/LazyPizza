package com.lihan.lazypizza.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrderHistoryEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val userId: String,
    val createAt: Long,
    val totalAmount: Double,
    val orderNumber: Long,
    val status: Int,
    val details: String
)
