package com.lihan.lazypizza.auth.di

import com.lihan.lazypizza.auth.presentation.LoginViewModel
import com.lihan.lazypizza.auth.presentation.util.FirebaseAuthManager
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    single { FirebaseAuthManager(get()) }
    viewModelOf(::LoginViewModel)
}