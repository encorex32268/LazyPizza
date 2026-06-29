package com.lihan.lazypizza.cart.presentation.order_checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class OrderCheckoutViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(OrderCheckoutState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = OrderCheckoutState()
        )

    fun onAction(action: OrderCheckoutAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

}