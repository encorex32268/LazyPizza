package com.lihan.lazypizza.cart.presentation

sealed interface CartSharedAction {
    data class OnPlusClick(val cartItemId: Long): CartSharedAction
    data class OnMinusClick(val cartItemId: Long): CartSharedAction
    data class OnDeleteClick(val cartItemId: Long, val productId: String): CartSharedAction
    data class OnAddItemClick(val productId: String): CartSharedAction
    data object OnPlaceOrderClick: CartSharedAction
    data object OnDismissErrorDialog: CartSharedAction
}