package com.lihan.lazypizza

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.lazypizza.core.domain.CartRepository
import com.lihan.lazypizza.core.domain.UserDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val userDataStore: UserDataStore,
    private val cartRepository: CartRepository
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(MainState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData){
                observeCartItemCount()
                hasLoadedInitialData = true
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            MainState()
        )

    init {
        //Check user status before ui showing
        checkUserStatus()
    }

    private fun checkUserStatus(){
        userDataStore
            .getUserPhoneNumber()
            .onEach { phoneNumber ->
                _state.update { it.copy(
                    isLogin = phoneNumber.isNotEmpty(),
                    isLoaded = true
                ) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeCartItemCount(){
        cartRepository
            .getCartItems()
            .onEach { cartItemWithToppings ->
                _state.update { it.copy(
                    cartItemCount = cartItemWithToppings.size
                ) }

            }.launchIn(viewModelScope)
    }
}