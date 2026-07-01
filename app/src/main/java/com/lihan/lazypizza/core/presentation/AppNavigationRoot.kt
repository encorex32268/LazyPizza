package com.lihan.lazypizza.core.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.lihan.lazypizza.TestScreen
import com.lihan.lazypizza.auth.presentation.LoginRoot
import com.lihan.lazypizza.cart.presentation.CartRoot
import com.lihan.lazypizza.cart.presentation.CartSharedViewModel
import com.lihan.lazypizza.cart.presentation.order_checkout.OrderCheckoutRoot
import com.lihan.lazypizza.core.analytics.LocalAnalyticsHelper
import com.lihan.lazypizza.history.presentation.HistoryRoot
import com.lihan.lazypizza.menu.presentation.MenuRoot
import com.lihan.lazypizza.menu.presentation.product_detail.ProductDetailRoot
import org.koin.androidx.compose.koinViewModel

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
//            startDestination = Route.TestComponents,
            startDestination = if(isLogin){
                Route.MenuGraph
            }else{
                Route.AuthGraph
            },
        ){

            composable<Route.TestComponents>(){
                TestScreen()
            }

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
                    val parentEntry = navController.rememberParentEntry<Route.CartGraph>()
                    val cartSharedViewModel = koinViewModel<CartSharedViewModel>(
                        viewModelStoreOwner = parentEntry
                    )

                    CartRoot(
                        onNavigateToOrderCheckout = {
                            navController.navigate(Route.OrderCheckout)
                        },
                        sharedViewModel = cartSharedViewModel,
                        onBackToMenu = {
                            navController.backToMenu()
                        }
                    )
                }
                composable<Route.OrderCheckout>{
                    val parentEntry = navController.rememberParentEntry<Route.CartGraph>()
                    val cartSharedViewModel = koinViewModel<CartSharedViewModel>(
                        viewModelStoreOwner = parentEntry
                    )
                    OrderCheckoutRoot(
                        sharedViewModel = cartSharedViewModel,
                        onBack = { navController.navigateUp() },
                        onNavigateToMenu = {
                            navController.backToMenu()
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
                            navController.backToMenu()
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

private fun NavHostController.backToMenu() {
    this.navigate(Route.Menu) {
        popUpTo(Route.Menu) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
inline fun <reified T : Any> NavController.rememberParentEntry(): NavBackStackEntry {
    return remember(this) {
        this.getBackStackEntry<T>()
    }
}