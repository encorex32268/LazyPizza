package com.lihan.lazypizza.auth.presentation

import androidx.compose.foundation.text.input.TextFieldState
import kotlin.time.Duration

data class LoginState(
    val phoneNumberTextField: TextFieldState = TextFieldState("+886 930 111 222"),
    val isEnabledContinueButton: Boolean = false,
    val loginStatus: LoginStatus = LoginStatus.PhoneNumberInput,
    val otpTextFieldStates: List<TextFieldState> = (0..5).map { TextFieldState() },
    val isOtpInvalid: Boolean = false,
    val smsValidTime: Duration = Duration.ZERO,
){

    val timeString: String
        get() {
            return String.format(
                locale = java.util.Locale.getDefault(),
                format = "%02d:%02d",
                smsValidTime.inWholeMinutes,
                smsValidTime.inWholeSeconds % 60
            )
        }
}


enum class LoginStatus{
    Loading,
    PhoneNumberInput,
    SMSCodeInput,
}