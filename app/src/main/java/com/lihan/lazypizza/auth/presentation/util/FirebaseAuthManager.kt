package com.lihan.lazypizza.auth.presentation.util

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.lihan.lazypizza.core.domain.UserDataStore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit


class FirebaseAuthManager(
    private val userDataStore: UserDataStore
){

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signIn(
        phoneNumber: String,
        activity: Activity,
    ): Flow<PhoneAuthResult> = callbackFlow {

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithCredential(credential = credential){
                    trySend(it)
                    close()
                }
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                exception.printStackTrace()
                trySend(PhoneAuthResult.Error(exception))
                close(exception)
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationId, token)
                trySend(PhoneAuthResult.CodeSent(verificationId))

            }

        }
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)

        awaitClose{

        }
    }

    suspend fun verifySmsCode(smsCode: String,verificationId: String): PhoneAuthResult {

        val credential = PhoneAuthProvider.getCredential(verificationId, smsCode)

        return try {
            val authResult = auth.signInWithCredential(credential).await()

            if (authResult.user == null){
                PhoneAuthResult.Error(Exception("AuthResult user is null!"))
            }else{
                PhoneAuthResult.Verified(authResult.user!!)
            }
        }
        catch (_ : FirebaseAuthInvalidCredentialsException){
            PhoneAuthResult.CodeInvalid
        }
        catch (e: Exception) {
            e.printStackTrace()
            PhoneAuthResult.Error(e)
        }
    }

    fun signInWithCredential(
        credential: PhoneAuthCredential,
        onResult: (PhoneAuthResult) -> Unit
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result?.user == null){
                        onResult(PhoneAuthResult.Error(task.exception ?: Exception("Auto sign-in failed")))
                    }else{
                        onResult(PhoneAuthResult.Verified(task.result?.user!!))
                    }
                } else {
                    onResult(PhoneAuthResult.Error(task.exception ?: Exception("Auto sign-in failed")))
                }
            }
    }

    suspend fun signOut() {
        userDataStore.clearUserData()
        auth.signOut()
    }
}