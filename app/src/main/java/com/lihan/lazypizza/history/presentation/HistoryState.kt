package com.lihan.lazypizza.history.presentation

import com.lihan.lazypizza.history.presentation.model.OrderHistoryUi

data class HistoryState(
    val items: List<OrderHistoryUi> = emptyList(),
    val isSignIn: Boolean = false,
    val isLoading: Boolean = true,
)