package com.lihan.lazypizza.menu.presentation.model

import com.lihan.lazypizza.menu.presentation.ProductType

data class ProductUi(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Double,
    val type: ProductType,
    val isEditingMode: Boolean = false,
    val count: Int = if (isEditingMode) 1 else 0,
){
    val priceString: String = "$$price"

    val priceTotal: String = "$${(count * price)}"

    val priceTotalDetail: String = "$count x $priceString"
}
