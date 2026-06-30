package com.lihan.lazypizza.menu.presentation.model

import com.lihan.lazypizza.core.domain.formatToTwoDecimals
import com.lihan.lazypizza.menu.presentation.ProductType

data class ProductUi(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Double,
    val category: String,
    val isEditingMode: Boolean = false,
    val count: Int = if (isEditingMode) 1 else 0,
){
    val type: ProductType
        get() = ProductType.fromString(category)

    val priceString: String
        get() = "$${price.formatToTwoDecimals()}"

    val priceTotal: String
        get() {
            val result = count * price
            return "$${result.formatToTwoDecimals()}"
        }

    val priceTotalDetail: String
        get() = "$count x $priceString"
}
