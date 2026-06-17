package com.lihan.lazypizza.history.presentation

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

class HistoryViewModel(
    private val userDataStore: UserDataStore
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HistoryState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeUserStatus()
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

    private fun observeUserStatus(){
        //TODO Combine HistoryData and UserDataStore
        userDataStore
            .getUserPhoneNumber()
            .onEach { phoneNumber ->
                _state.update { it.copy(
                    isSignIn = phoneNumber.isNotEmpty()
                ) }
            }
            .launchIn(viewModelScope)
    }

}