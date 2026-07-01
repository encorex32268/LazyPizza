package com.lihan.lazypizza.cart.di

import com.lihan.lazypizza.cart.presentation.CartSharedViewModel
import com.lihan.lazypizza.cart.presentation.order_checkout.OrderCheckoutViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val cartModule = module {
    viewModelOf(::CartSharedViewModel)
    viewModelOf(::OrderCheckoutViewModel)
}