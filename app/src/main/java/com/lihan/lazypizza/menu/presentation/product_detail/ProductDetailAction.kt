package com.lihan.lazypizza.menu.presentation.product_detail

sealed interface ProductDetailAction {
    data class OnPlusClick(val id: String): ProductDetailAction
    data class OnMinusClick(val id: String): ProductDetailAction
    data class OnItemClick(val id: String): ProductDetailAction
    data object OnBack: ProductDetailAction
}