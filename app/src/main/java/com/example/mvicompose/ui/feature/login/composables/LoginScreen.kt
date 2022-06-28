package com.example.mvicompose.ui.feature.login.composables

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager.*
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.mvicompose.R
import com.example.mvicompose.cryptography.BiometricPromptUtils
import com.example.mvicompose.ui.base.SIDE_EFFECTS_KEY
import com.example.mvicompose.ui.common.Progress
import com.example.mvicompose.ui.feature.login.LoginContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun LoginScreen(
    state: LoginContract.State,
    effectFlow: Flow<LoginContract.Effect>?,
    onEventSent: (event: LoginContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: LoginContract.Effect.Navigation) -> Unit
) {

    val context = LocalContext.current
    val biometricManager = from(context)
    val addBiometricsResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            if (biometricManager.canAuthenticate(BIOMETRIC_STRONG) == BIOMETRIC_SUCCESS) {
                onEventSent(LoginContract.Event.LoginButtonClick)
            }
        })

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is LoginContract.Effect.ShowBiometricsPrompt -> {
                    when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
                        BIOMETRIC_SUCCESS -> {
                            val biometricPrompt =
                                BiometricPromptUtils.createBiometricPrompt(context) { authResult ->
                                    onEventSent(
                                        LoginContract.Event.BiometricAuthenticationResult(
                                            authResult,
                                            context.applicationContext
                                        )
                                    )
                                }
                            val promptInfo = BiometricPromptUtils.createPromptInfo(context)
                            biometricPrompt?.authenticate(promptInfo, effect.cryptoObject)
                        }
                        BIOMETRIC_ERROR_NONE_ENROLLED -> {
                            addBiometricsResult.launch(BiometricPromptUtils.getEnrollBiometricsIntent())
                        }
                    }
                }
                is LoginContract.Effect.Navigation -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        colorResource(id = R.color.purple_700),
                        colorResource(id = R.color.purple_200)
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.primary
            ),
            onClick = { onEventSent(LoginContract.Event.LoginButtonClick) }
        ) {
            Text(text = stringResource(id = R.string.login))
        }

        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))

        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.onPrimary,
                contentColor = MaterialTheme.colors.primary
            ),
            onClick = { onEventSent(LoginContract.Event.PreviewButtonClick) }
        ) {
            Text(text = stringResource(id = R.string.preview_mode))
        }
    }

    if (state.isLoading) {
        Progress()
    }
}