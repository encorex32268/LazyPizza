package com.lihan.lazypizza

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.lazypizza.core.domain.UserDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val userDataStore: UserDataStore
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(MainState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                checkUserStatus()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MainState()
        )


    private fun checkUserStatus(){
        userDataStore
            .getUserPhoneNumber()
            .onEach { phoneNumber ->
                _state.update { it.copy(
                    isLogin = phoneNumber.isNotEmpty()
                ) }
            }
            .launchIn(viewModelScope)
    }
}