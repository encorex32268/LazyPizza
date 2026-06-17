package com.lihan.lazypizza.auth.presentation

import android.app.Activity

sealed interface LoginAction {
    data class OnConfirmClick(val activity: Activity?): LoginAction
    data object OnWithoutSignInClick: LoginAction
    data class OnResendCodeClick(val activity: Activity?): LoginAction
    data object OnCodeInputted: LoginAction
    data object OnRefresh: LoginAction

}