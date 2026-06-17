package com.lihan.lazypizza.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lihan.lazypizza.R
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    data object AuthGraph: Route

    @Serializable
    data object Login: Route

    @Serializable
    data object MenuGraph: Route

    @Serializable
    data object Menu: Route

    @Serializable
    data class ProductDetail(val id: String): Route

    @Serializable
    data object CartGraph: Route

    @Serializable
    data object Cart: Route

    @Serializable
    data object HistoryGraph: Route

    @Serializable
    data object History: Route
}

@Composable
fun Route.toRouteName(): String{
    return when(this){
        Route.Cart -> stringResource(R.string.cart)
        Route.History -> stringResource(R.string.history)
        Route.Menu -> stringResource(R.string.menu)
        else -> ""
    }
}

