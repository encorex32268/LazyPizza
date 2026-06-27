package com.lihan.lazypizza.auth.presentation

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.integrity.internal.ac
import com.lihan.lazypizza.auth.domain.PhoneNumberVerify
import com.lihan.lazypizza.auth.presentation.util.FirebaseAuthManager
import com.lihan.lazypizza.auth.presentation.util.PhoneAuthResult
import com.lihan.lazypizza.core.domain.UserDataStore
import com.lihan.lazypizza.core.domain.util.TimerFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class LoginViewModel(
    private val userDataStore: UserDataStore,
    private val firebaseAuthManager: FirebaseAuthManager
) : ViewModel() {


    private var hasLoadedInitialData = false

    private val _uiEvent = Channel<LoginUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeLoginStatus()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = LoginState()
        )


    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnResendCodeClick -> sendPhoneNumber(action.activity)
            is LoginAction.OnConfirmClick -> sendPhoneNumber(action.activity)
            LoginAction.OnWithoutSignInClick -> withoutSignIn()
            LoginAction.OnCodeInputted -> verifyOtpCode()
            LoginAction.OnRefresh -> { _state.update { LoginState() } }
        }
    }

    private fun withoutSignIn() {
        viewModelScope.launch {
            _uiEvent.send(LoginUiEvent.NavigateToMenu)
        }
    }

    private fun observeLoginStatus() {
        combine(
            _state.map { it.loginStatus },
            snapshotFlow { _state.value.phoneNumberTextField.text.toString() },
            snapshotFlow { _state.value.otpTextFieldStates.map { it.text.toString() } }
        ){ loginStatus , phoneNumber, otpCode ->

            when(loginStatus){
                LoginStatus.PhoneNumberInput -> {
                    val verifyPhoneNumber = PhoneNumberVerify.verify(phoneNumber)
                    _state.update { it.copy(
                        isEnabledContinueButton = verifyPhoneNumber
                    ) }
                }
                LoginStatus.SMSCodeInput -> {
                    _state.update { it.copy(
                        isEnabledContinueButton = otpCode.joinToString(separator = "").length == 6
                    ) }
                }
                LoginStatus.Loading -> {
                    _state.update { it.copy(
                        isEnabledContinueButton = false
                    ) }
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun verifyOtpCode() {
        viewModelScope.launch {

            val verificationId = userDataStore.getVerificationId().first()
            if (verificationId.isEmpty()) {
                println("VerificationId is empty !")
                return@launch
            }

            val code = _state.value.otpTextFieldStates.joinToString(separator = "") { it.text.toString() }

            val result =  firebaseAuthManager.verifySmsCode(
                smsCode = code,
                verificationId = verificationId
            )
            when (result) {
                PhoneAuthResult.CodeInvalid -> {
                    _state.update {
                        it.copy(
                            isOtpInvalid = true
                        )
                    }
                }
                is PhoneAuthResult.Verified -> {
                    val firebaseUser = result.user
                    userDataStore.setUserId(firebaseUser.uid)
                    println("UserId: ${firebaseUser.uid} Saved ! ")
                    firebaseUser.phoneNumber?.let {
                        println("PhoNumber: $it Saved ! ")
                        userDataStore.setUserPhoneNumber(it)
                    }
                    delay(300L)
                    _uiEvent.send(LoginUiEvent.VerifySucceed)
                }
                else -> Unit
            }
        }
    }

    private fun sendPhoneNumber(
        activity: Activity?,
    ) {
        if (activity == null) {
            //TODO: Error Log or Not ?
            println("Activity Not Found !")
            return
        }
        _state.update { it.copy(
            loginStatus = LoginStatus.Loading
        ) }

        //clean OTP TextField
        cleanOTPTextField()


        val phoneNumber = state.value.phoneNumberTextField.text.toString()

        viewModelScope.launch {
            val phoneAuthResult = firebaseAuthManager.signIn(
                phoneNumber = phoneNumber,
                activity = activity
            ).first()

            when (phoneAuthResult) {
                is PhoneAuthResult.CodeSent -> {
                    userDataStore.setVerificationId(phoneAuthResult.verificationId)
                    startTimer()
                    _state.update {
                        it.copy(
                            loginStatus = LoginStatus.SMSCodeInput
                        )
                    }
                }

                is PhoneAuthResult.Error -> {
                    _state.update { it.copy(
                        loginStatus = LoginStatus.PhoneNumberInput
                    ) }
                }

                is PhoneAuthResult.Verified -> {
                    //TODO: Send UiEvent Navigate To Menu
                    println("Verified: User PhoneNumber ${phoneAuthResult.user.phoneNumber}")
                    _uiEvent.send(LoginUiEvent.VerifySucceed)
                }

                else -> Unit
            }
        }
    }

    private fun cleanOTPTextField() {
        _state.value.otpTextFieldStates.forEach {
            it.clearText()
        }
    }

    private fun startTimer() {
        TimerFlow
            .timeAndEmit()
            .onStart {
                _state.update { it.copy(
                    smsValidTime = 59.seconds
                ) }
            }
            .takeWhile { _state.value.smsValidTime > Duration.ZERO }
            .onEach { duration ->
                val newTime = state.value.smsValidTime - duration

                _state.update {
                    it.copy(
                        smsValidTime = if (newTime <= Duration.ZERO) {
                            Duration.ZERO
                        } else {
                            newTime
                        }
                    )
                }
            }.launchIn(viewModelScope)
    }
}