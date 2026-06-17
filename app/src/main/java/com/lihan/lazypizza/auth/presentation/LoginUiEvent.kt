package com.lihan.lazypizza.auth.presentation

sealed interface LoginUiEvent {
    data object VerifySucceed: LoginUiEvent
    data object NavigateToMenu: LoginUiEvent
}