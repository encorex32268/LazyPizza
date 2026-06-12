package com.lihan.lazypizza.menu.presentation.product_detail

import com.lihan.lazypizza.menu.presentation.model.ProductUi
import com.lihan.lazypizza.menu.presentation.model.ToppingUi

data class ProductDetailState(
    val product: ProductUi? = null,
    val toppings: List<ToppingUi> = emptyList(),
    val totalString: String = ""
)