package com.lihan.lazypizza.history.presentation

data class HistoryState(
    val items: List<String> = emptyList(),
    val isSignIn: Boolean = false
)