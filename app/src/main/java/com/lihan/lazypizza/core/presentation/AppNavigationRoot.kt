package com.lihan.lazypizza.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.lihan.lazypizza.menu.presentation.MenuRoot
import com.lihan.lazypizza.menu.presentation.product_detail.ProductDetailRoot

@Composable
fun AppNavigationRoot(
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
            startDestination = Route.MenuGraph,
        ){
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
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Cart"
                        )
                    }
                }
            }

            navigation<Route.HistoryGraph>(
                startDestination = Route.History
            ){
                composable<Route.History>{
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "History"
                        )
                    }
                }
            }
        }

    }
}
