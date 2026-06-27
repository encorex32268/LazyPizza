package com.lihan.lazypizza.core.domain.util

sealed interface RemoteError: RootError {
    data object UnknownUser: RemoteError
    data object CreateOrderFailed: RemoteError
    data object CreateOrderCanceled: RemoteError
}