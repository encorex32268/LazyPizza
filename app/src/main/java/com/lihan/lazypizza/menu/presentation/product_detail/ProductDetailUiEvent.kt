package com.lihan.lazypizza.menu.presentation.product_detail

sealed interface ProductDetailUiEvent {
    data object OnBack: ProductDetailUiEvent
}