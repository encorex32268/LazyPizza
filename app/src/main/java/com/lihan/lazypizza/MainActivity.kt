@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lihan.lazypizza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.lihan.lazypizza.core.domain.StoreProductRepository
import com.lihan.lazypizza.core.domain.util.TimerFlow
import com.lihan.lazypizza.core.presentation.AppNavigationRoot
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.menu.presentation.MenuRoot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.compose.KoinContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


class TimerViewModel: ViewModel() {

    var totalTime = MutableStateFlow(60.seconds)
        private set

    init {
        TimerFlow
            .timeAndEmit()
            .takeWhile { totalTime.value > Duration.ZERO }
            .onEach { mills ->
                totalTime.value -= mills

                if (totalTime.value < Duration.ZERO) {
                    totalTime.value = Duration.ZERO
                }
            }.launchIn(viewModelScope)
    }
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            LazyPizzaTheme {
                //Avoid this
                //No Compose Koin context setup, taking default. Use KoinContext(), KoinAndroidContext() or KoinApplication() function to setup or create Koin context and avoid such message.
                KoinContext {
                    AppNavigationRoot()
                }
            }
        }
    }
}