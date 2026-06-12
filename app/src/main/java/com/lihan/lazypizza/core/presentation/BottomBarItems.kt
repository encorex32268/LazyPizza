package com.lihan.lazypizza.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector


data class BottomBarItem(
    val route: Route,
    val icon: ImageVector,
    val badge: Int = 0
)


val bottomBarItems: List<BottomBarItem>
    @Composable
    get() = listOf(
        BottomBarItem(route = Route.Menu , icon = BookOpenFilled),
        BottomBarItem(route = Route.Cart , icon = ShoppingCartFilled),
        BottomBarItem(route = Route.History , icon = ClockFilled),
    )