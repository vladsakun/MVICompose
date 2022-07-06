package com.example.mvicompose.ui.feature.main

import android.app.Application
import android.content.Context
import androidx.biometric.BiometricPrompt
import androidx.lifecycle.viewModelScope
import com.example.mvicompose.common.CIPHERTEXT_WRAPPER
import com.example.mvicompose.common.CVV
import com.example.mvicompose.common.SECRET_KEY_NAME
import com.example.mvicompose.common.SHARED_PREFS_FILENAME
import com.example.mvicompose.cryptography.CryptographyManagerImpl
import com.example.mvicompose.ui.base.BaseAndroidViewModel
import com.example.mvicompose.ui.feature.main.MainContract.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(application: Application) :
    BaseAndroidViewModel<Event, State, Effect>(application) {

    override fun setInitialState() = State.getDefaultState()
    private val cryptographyManager = CryptographyManagerImpl()

    private fun initName() {
        viewModelScope.launch(Dispatchers.IO) {
            setState { copy(isLoading = true) }
            val name = getUserName()
            setState { copy(isLoading = false, name = generateHelloString(name)) }
        }
    }

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.GraphQLButtonClick -> setEffect { Effect.Navigation.ToPreview }
            is Event.BiometricAuthenticationResult -> biometricAuthenticationResult(event.authenticationResult)
            is Event.LoginButtonClick -> setEffect {
                Effect.ShowBiometricsPrompt(
                    BiometricPrompt.CryptoObject(
                        cryptographyManager.getInitializedCipherForEncryption(SECRET_KEY_NAME)
                    )
                )
            }
            is Event.InitName -> initName()
        }
    }

    private fun biometricAuthenticationResult(authenticationResult: BiometricPrompt.AuthenticationResult) {
        viewModelScope.launch(Dispatchers.IO) {
            setState { copy(isLoading = true) }

            // fetch CVV from server
            val cvv = fetchCVV()

            setState { copy(isLoading = false) }

            // encrypting CVV according to user biometrics
            authenticationResult.cryptoObject?.cipher?.run {
                val encryptedCVVWrapper = cryptographyManager.encryptData(cvv, this)
                cryptographyManager.persistCiphertextWrapperToSharedPrefs(
                    encryptedCVVWrapper,
                    getApplication(),
                    SHARED_PREFS_FILENAME,
                    Context.MODE_PRIVATE,
                    CIPHERTEXT_WRAPPER
                )
            }

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

    private external fun generateHelloString(name: String): String

    companion object {
        init {
            System.loadLibrary("mvicompose")
        }
    }
}