package com.lihan.lazypizza.auth.domain

import com.lihan.lazypizza.core.domain.util.RootError

sealed interface AuthError: RootError{
    data object ExistAccount: AuthError
}