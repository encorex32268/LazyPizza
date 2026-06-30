package com.lihan.lazypizza.auth.presentation

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lihan.lazypizza.R
import com.lihan.lazypizza.auth.presentation.components.PhoneNumberTextField
import com.lihan.lazypizza.auth.presentation.components.SecureTextFields
import com.lihan.lazypizza.core.presentation.design_system.AppButton
import com.lihan.lazypizza.core.presentation.design_system.ButtonType
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body3Regular
import com.lihan.lazypizza.core.presentation.ui.theme.title1Medium
import com.lihan.lazypizza.core.presentation.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel
import kotlin.time.Duration

@Composable
fun LoginRoot(
    navigateToMenu: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.uiEvent) { uiEvent ->
        when(uiEvent){
            LoginUiEvent.NavigateToMenu,
            LoginUiEvent.VerifySucceed -> navigateToMenu()
        }
    }

    LoginScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isPreview = LocalInspectionMode.current

    val activity = if (isPreview) null else LocalActivity.current

    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    BackHandler(
        onBack = {
            onAction(LoginAction.OnRefresh)
        }
    )

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(top = 184.dp)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.welcome_to_lazy_pizza),
                style = MaterialTheme.typography.title1Medium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.enter_your_phone_number),
                style = MaterialTheme.typography.body3Regular.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            Spacer(Modifier.height(20.dp))
            PhoneNumberTextField(
                modifier = Modifier.fillMaxWidth(),
                textFieldState = state.phoneNumberTextField
            )
            if (state.loginStatus == LoginStatus.SMSCodeInput ){
                Spacer(Modifier.height(12.dp))
                AnimatedVisibility(visible = true){
                    SecureTextFields(
                        textFieldStates = state.otpTextFieldStates,
                        isOtpInvalid = state.isOtpInvalid
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                text = if (state.loginStatus == LoginStatus.SMSCodeInput){
                    stringResource(R.string.confirm)
                }else{
                    stringResource(R.string.continue_text)
                },
                type = ButtonType.Filled,
                enabled = state.isEnabledContinueButton,
                onClick = {
                    focusManager.clearFocus()
                    keyboard?.hide()

                    if (state.loginStatus == LoginStatus.SMSCodeInput){
                        onAction(LoginAction.OnCodeInputted)
                    }else{
                        onAction(LoginAction.OnConfirmClick(activity = activity))
                    }

                }
            )
            Spacer(Modifier.height(12.dp))
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.continue_without_signing_in),
                type = ButtonType.Text,
                onClick = {
                    onAction(LoginAction.OnWithoutSignInClick)
                },
            )

            if (
                state.smsValidTime != Duration.ZERO &&
                state.loginStatus == LoginStatus.SMSCodeInput
                ){
                Text(
                    text = stringResource(R.string.you_can_request_new_code,state.timeString),
                    style = MaterialTheme.typography.body3Regular.copy(
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            }

            AnimatedVisibility(
                visible = state.smsValidTime == Duration.ZERO &&  state.loginStatus == LoginStatus.SMSCodeInput
            ) {
                Spacer(Modifier.height(12.dp))
                AppButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.resend_code),
                    type = ButtonType.Text,
                    onClick = {
                        onAction(LoginAction.OnResendCodeClick(activity = activity))
                    },
                )
            }

        }

        if (state.loginStatus == LoginStatus.Loading){
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.3f
                    ))
                    .wrapContentSize()
            )
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LazyPizzaTheme {
        LoginScreen(
            state = LoginState(
                loginStatus = LoginStatus.Loading
            ),
            onAction = {}
        )
    }
}