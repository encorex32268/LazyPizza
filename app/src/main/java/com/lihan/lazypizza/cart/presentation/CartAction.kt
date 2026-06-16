package com.lihan.lazypizza.cart.presentation

sealed interface CartAction {
    data class OnPlusClick(val cartItemId: Long): CartAction
    data class OnMinusClick(val cartItemId: Long): CartAction
    data class OnDeleteClick(val cartItemId: Long, val productId: String): CartAction
    data class OnAddItemClick(val productId: String): CartAction
    data object OnBackToMenu: CartAction
}