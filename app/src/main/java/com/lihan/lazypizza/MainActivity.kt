package com.lihan.lazypizza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.lihan.lazypizza.core.domain.StoreProductRepository
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {

    private val storeProductRepository by inject<StoreProductRepository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            storeProductRepository.getProducts().collect {
                println(">> ${it}")
            }
        }

        lifecycleScope.launch {
            storeProductRepository.getToppings().collect {
                println("Toppings: ${it}")
            }
        }

        setContent {
            LazyPizzaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                }
            }
        }
    }
}