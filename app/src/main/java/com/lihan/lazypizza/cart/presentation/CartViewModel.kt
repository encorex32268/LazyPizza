package com.lihan.lazypizza.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.lazypizza.core.domain.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(CartState())
    val state = _state.onStart {
        if (!hasLoadedInitialData) {
            /** init */
            hasLoadedInitialData = true
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CartState()
        )

    init {

        viewModelScope.launch {
            cartRepository
                .getCartItems()
                .collectLatest { dbCartItemToppings ->
                    dbCartItemToppings.forEach { cartItemWithToppings ->

                        println("-----")
                        println("MainItem: ${cartItemWithToppings.cartItem}")
                        println("MainItem's Toppings: ${cartItemWithToppings.toppings}")
                        println("-----")

                    }
                }


        }


    }

    fun onAction(action: CartAction) {
        when (action) {
            else -> Unit
        }
    }
}