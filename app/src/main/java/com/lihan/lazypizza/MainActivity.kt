@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lihan.lazypizza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lihan.lazypizza.core.presentation.AppNavigationRoot
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {

    private val mainViewModel by inject<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            !mainViewModel.state.value.isLoaded
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LazyPizzaTheme {

                val state by mainViewModel.state.collectAsStateWithLifecycle()

                //Avoid this
                //No Compose Koin context setup, taking default. Use KoinContext(), KoinAndroidContext() or KoinApplication() function to setup or create Koin context and avoid such message.
                KoinContext {
                    if (state.isLoaded) {
                        AppNavigationRoot(
                            isLogin = state.isLogin,
                            cartItemCount = state.cartItemCount
                        )
                    }
                }
            }
        }
    }
}