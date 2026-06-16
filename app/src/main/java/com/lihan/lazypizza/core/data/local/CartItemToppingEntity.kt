package com.lihan.lazypizza.core.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//Id	CartItemId	toppingId	Quantity
//1		1	“topping_5”		1
//1		1	“topping_3”		3
//1		2	“topping_1”		3
//1		2	“topping_4”		2

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CartItemEntity::class,
            parentColumns = ["cartItemId"],
            childColumns = ["cartItemId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CartItemToppingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cartItemId: Long,
    val toppingId: String,
    val quantity: Int,
    val name: String,
    val price: Double
)