package com.example.mvicompose.ui.feature.login

import android.content.Context
import androidx.biometric.BiometricPrompt
import androidx.lifecycle.viewModelScope
import com.example.mvicompose.common.CIPHERTEXT_WRAPPER
import com.example.mvicompose.common.CVV
import com.example.mvicompose.common.SECRET_KEY_NAME
import com.example.mvicompose.common.SHARED_PREFS_FILENAME
import com.example.mvicompose.cryptography.CryptographyManagerImpl
import com.example.mvicompose.ui.base.BaseViewModel
import com.example.mvicompose.ui.feature.login.LoginContract.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State.getDefaultState()
    private val cryptographyManager = CryptographyManagerImpl()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val name = getUserName()
            setState { copy(isLoading = false, name = generateHelloString(name)) }
        }
    }

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.PreviewButtonClick -> setEffect { Effect.Navigation.ToPreview }
            is Event.BiometricAuthenticationResult -> biometricAuthenticationResult(
                event.authenticationResult,
                event.applicationContext
            )
            is Event.LoginButtonClick -> setEffect {
                Effect.ShowBiometricsPrompt(
                    BiometricPrompt.CryptoObject(
                        cryptographyManager.getInitializedCipherForEncryption(SECRET_KEY_NAME)
                    )
                )
            }
        }
    }

    private fun biometricAuthenticationResult(
        authenticationResult: BiometricPrompt.AuthenticationResult,
        applicationContext: Context
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            setState { copy(isLoading = true) }

            // fetch CVV from server
            val cvv = fetchCVV()

            // encrypting CVV according to user biometrics
            authenticationResult.cryptoObject?.cipher?.run {
                val encryptedCVVWrapper = cryptographyManager.encryptData(cvv, this)
                cryptographyManager.persistCiphertextWrapperToSharedPrefs(
                    encryptedCVVWrapper,
                    applicationContext,
                    SHARED_PREFS_FILENAME,
                    Context.MODE_PRIVATE,
                    CIPHERTEXT_WRAPPER
                )
            }

            setState { copy(isLoading = false) }

            setEffect { Effect.Navigation.ToMoviesScreen }
        }
    }

    private suspend fun fetchCVV(): String {
        delay(500)
        return CVV
    }

    private suspend fun getUserName(): String {
        delay(200)
        return "Vlad"
    }

    external fun generateHelloString(name: String): String

    companion object {
        init {
            System.loadLibrary("mvicompose")
        }
    }
}