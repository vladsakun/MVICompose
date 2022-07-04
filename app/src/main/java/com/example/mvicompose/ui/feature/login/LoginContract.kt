package com.example.mvicompose.ui.feature.login

import android.content.Context
import androidx.biometric.BiometricPrompt
import com.example.mvicompose.ui.base.ViewEvent
import com.example.mvicompose.ui.base.ViewSideEffect
import com.example.mvicompose.ui.base.ViewState

class LoginContract {

    sealed class Event : ViewEvent {
        object PreviewButtonClick : Event()
        object LoginButtonClick : Event()
        class BiometricAuthenticationResult(
            val authenticationResult: BiometricPrompt.AuthenticationResult,
            // May be improved via AndroidViewModel or DI
            val applicationContext: Context
        ) : Event()
    }

    data class State(
        val isLoading: Boolean,
        val name: String
    ) : ViewState {

        companion object {
            fun getDefaultState() = State(true, "")
        }
    }

    sealed class Effect : ViewSideEffect {

        class ShowBiometricsPrompt(
            val cryptoObject: BiometricPrompt.CryptoObject
        ) : Effect()

        sealed class Navigation : Effect() {
            object ToPreview : Navigation()
            object ToMoviesScreen : Navigation()
        }
    }
}