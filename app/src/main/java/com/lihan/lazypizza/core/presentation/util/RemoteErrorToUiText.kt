package com.lihan.lazypizza.core.presentation.util

import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.domain.util.RemoteError
import com.lihan.lazypizza.core.presentation.ui.util.UiText

fun RemoteError.toUiText(): UiText {
    return when(this) {
        RemoteError.CreateOrderCanceled -> UiText.StringResource(R.string.error_order_canceled)
        RemoteError.CreateOrderFailed -> UiText.StringResource(R.string.error_order_failed)
        RemoteError.UnknownUser -> UiText.StringResource(R.string.error_order_unknown_user)
    }
}