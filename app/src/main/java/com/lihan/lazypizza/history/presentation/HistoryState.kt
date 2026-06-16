package com.lihan.lazypizza.history.presentation

data class HistoryState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)