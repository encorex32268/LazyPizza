package com.lihan.lazypizza.menu.presentation.model

import com.lihan.lazypizza.menu.presentation.ProductType
import java.util.Locale.getDefault

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
    val priceString: String
        get() = "$${String.format(getDefault(),"%.2f", price)}"

    val priceTotal: String
        get() {
            val result = count * price
            return "$${String.format(getDefault(),"%.2f", result)}"
        }

    val priceTotalDetail: String
        get() = "$count x $priceString"
}
