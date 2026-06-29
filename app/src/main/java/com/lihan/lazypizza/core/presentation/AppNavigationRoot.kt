package com.lihan.lazypizza.core.presentation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.lihan.lazypizza.auth.presentation.LoginRoot
import com.lihan.lazypizza.cart.presentation.CartRoot
import com.lihan.lazypizza.history.presentation.HistoryRoot
import com.lihan.lazypizza.menu.presentation.MenuRoot
import com.lihan.lazypizza.menu.presentation.product_detail.ProductDetailRoot

@Composable
fun AppNavigationRoot(
    isLogin: Boolean,
    cartItemCount: Int,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = currentDestination?.run {
                hasRoute<Route.Menu>() ||
                hasRoute<Route.Cart>() ||
                hasRoute<Route.History>() ||
                hasRoute<Route.MenuGraph>() ||
                hasRoute<Route.CartGraph>() ||
                hasRoute<Route.HistoryGraph>()
    } ?: false


    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showBottomBar){

                val currentRoute = bottomBarItems.find {
                    currentDestination.hasRoute(it.route::class)
                }?.route ?: Route.Menu

                AppBottomBar(
                    cartItemCount = cartItemCount,
                    currentRoute = currentRoute,
                    onItemClick = { route ->
                        navController.navigate(route){
                            popUpTo(Route.Menu) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { padding ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding),
            navController = navController,
            startDestination = if(isLogin){
                Route.MenuGraph
            }else{
                Route.AuthGraph
            },
        ){
            navigation<Route.AuthGraph>(
                startDestination = Route.Login
            ){
                composable<Route.Login>{
                    LoginRoot(
                        navigateToMenu = {
                            navController.navigate(Route.Menu){
                                popUpTo(Route.Menu) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }

            navigation<Route.MenuGraph>(
                startDestination = Route.Menu
            ){
                composable<Route.Menu>{
                    MenuRoot(
                        onNavigateToDetail = { id ->
                            navController.navigate(Route.ProductDetail(id))
                        }
                    )
                }
                composable<Route.ProductDetail>{
                    ProductDetailRoot(
                        onBack = {
                            navController.navigateUp()
                        }
                    )
                }
            }

            navigation<Route.CartGraph>(
                startDestination = Route.Cart
            ){
                composable<Route.Cart>{
                    CartRoot(
                        onBackToMenu = {
                            navController.navigate(Route.Menu){
                                popUpTo(Route.Menu) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }

            navigation<Route.HistoryGraph>(
                startDestination = Route.History
            ){
                composable<Route.History>{
                    HistoryRoot(
                        navigateToMenu = {
                            navController.navigate(Route.Menu){
                                popUpTo(Route.Menu) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        navigateToLogin = {
                            navController.navigate(Route.AuthGraph){
                                popUpTo(Route.Menu) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }

    }
}
