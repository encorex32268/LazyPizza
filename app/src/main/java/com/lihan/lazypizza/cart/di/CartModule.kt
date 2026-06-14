package com.lihan.lazypizza.cart.di

import com.lihan.lazypizza.cart.presentation.CartViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val cartModule = module {
    viewModelOf(::CartViewModel)
}