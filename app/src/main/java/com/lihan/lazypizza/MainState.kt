package com.lihan.lazypizza

data class MainState(
    val isLogin: Boolean = false,
    val isLoaded: Boolean = false,
    val cartItemCount: Int = 0
)
