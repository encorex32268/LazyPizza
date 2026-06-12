package com.lihan.lazypizza.menu.presentation

sealed interface MenuUiEvent {
    data class OnNavigateToDetail(val id: String): MenuUiEvent
}