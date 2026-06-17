package com.lihan.lazypizza.auth.presentation.util

import com.google.firebase.auth.FirebaseUser

sealed interface PhoneAuthResult {
    data class CodeSent(val verificationId: String): PhoneAuthResult
    data object CodeInvalid: PhoneAuthResult
    data class Verified(val user: FirebaseUser) : PhoneAuthResult
    data class Error(val exception: Exception) : PhoneAuthResult
}

