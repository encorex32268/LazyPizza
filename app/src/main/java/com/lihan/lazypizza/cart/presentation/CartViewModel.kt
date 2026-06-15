package com.lihan.lazypizza.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.lazypizza.cart.presentation.mapper.toUi
import com.lihan.lazypizza.core.domain.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(CartState())
    val state = _state.onStart {
        if (!hasLoadedInitialData) {
            observeCart()
            hasLoadedInitialData = true
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CartState()
        )


    fun onAction(action: CartAction) {
        when (action) {
            else -> Unit
        }
    }

    private fun observeCart(){
        cartRepository
            .getCartItems()
            .map{ cartItemWithToppings ->
                cartItemWithToppings.mapNotNull { cartItemWithTopping ->
                    cartItemWithTopping.toUi()
                }
            }
            .onEach { cartItemWithToppingsUis ->

                _state.update { it.copy(
                    items = cartItemWithToppingsUis

                )}
            }
            .launchIn(viewModelScope)
    }
}