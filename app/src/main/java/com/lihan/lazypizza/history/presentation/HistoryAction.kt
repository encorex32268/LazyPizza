package com.lihan.lazypizza.history.presentation

sealed interface HistoryAction {
    data object OnNavigateToMenu: HistoryAction
    data object OnNavigateToLogin: HistoryAction
}