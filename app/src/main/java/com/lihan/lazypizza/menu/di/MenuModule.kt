package com.lihan.lazypizza.menu.di

import com.lihan.lazypizza.menu.presentation.MenuViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val menuModule = module {

    viewModelOf(::MenuViewModel)

}