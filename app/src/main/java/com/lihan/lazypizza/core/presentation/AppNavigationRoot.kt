package com.lihan.lazypizza.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lihan.lazypizza.core.domain.Route
import com.lihan.lazypizza.menu.presentation.MenuRoot
import com.lihan.lazypizza.menu.presentation.product_detail.ProductDetailRoot

@Composable
fun AppNavigationRoot(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        modifier = modifier,
        navController = navController,
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
}
