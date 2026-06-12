package com.lihan.lazypizza.menu.presentation

sealed interface MenuAction {
    data class OnPlusClick(val id: String): MenuAction
    data class OnMinusClick(val id: String): MenuAction
    data class OnDeleteClick(val id: String): MenuAction
    data class OnAddToCartClick(val id: String): MenuAction
    data class OnProductTypeClick(val type: ProductType): MenuAction
    data class OnPizzaClick(val id: String): MenuAction
}