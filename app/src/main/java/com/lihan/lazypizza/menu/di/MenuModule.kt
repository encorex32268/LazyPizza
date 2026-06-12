package com.lihan.lazypizza.menu.di

import com.lihan.lazypizza.menu.presentation.MenuViewModel
import com.lihan.lazypizza.menu.presentation.product_detail.ProductDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val menuModule = module {

    viewModelOf(::MenuViewModel)
    viewModelOf(::ProductDetailViewModel)

}