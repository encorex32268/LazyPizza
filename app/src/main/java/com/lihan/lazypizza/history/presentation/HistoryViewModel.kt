@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lihan.lazypizza.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.lazypizza.core.domain.OrderRepository
import com.lihan.lazypizza.core.domain.UserDataStore
import com.lihan.lazypizza.history.presentation.mapper.toUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.milliseconds

class HistoryViewModel(
    private val userDataStore: UserDataStore,
    private val orderRepository: OrderRepository
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HistoryState())
    val state = _state
        .onStart {
            //fetch remote Data
            orderRepository.fetchOrderHistories()

            if (!hasLoadedInitialData) {
                observeData()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HistoryState()
        )

    fun onAction(action: HistoryAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

    private fun observeData(){

        userDataStore
            .getUserId()
            .onStart {
                _state.update { it.copy(
                    isLoading = true
                ) }
            }
            .flatMapLatest {  userId ->
                delay(500.milliseconds)
                val isSignIn = userId.isNotEmpty()
                _state.update { it.copy(
                    isSignIn = isSignIn,
                    isLoading = isSignIn
                ) }
                if (isSignIn){
                    orderRepository.getOrderHistories()
                }else{
                    emptyFlow()
                }
            }
            .onEach { orderHistories ->

                val sortedItems = orderHistories.map { orderHistory ->
                    orderHistory.toUi()
                }.sortedByDescending { it.createAt }

                _state.update { it.copy(
                    items = sortedItems,
                    isLoading = false
                ) }
            }.launchIn(viewModelScope)

    }

}