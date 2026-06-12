package com.lihan.lazypizza.core.domain

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    data object Menu: Route

    @Serializable
    data class ProductDetail(val id: String): Route
}