package com.example.mvicompose.ui.feature.main

import androidx.biometric.BiometricPrompt
import com.example.mvicompose.ui.base.ViewEvent
import com.example.mvicompose.ui.base.ViewSideEffect
import com.example.mvicompose.ui.base.ViewState

class MainContract {

    sealed class Event : ViewEvent {
        object GraphQLButtonClick : Event()
        object LoginButtonClick : Event()
        class BiometricAuthenticationResult(
            val authenticationResult: BiometricPrompt.AuthenticationResult
        ) : Event()

        object InitName : Event()
    }

    data class State(
        val isLoading: Boolean,
        val name: String
    ) : ViewState {

        companion object {
            fun getDefaultState() = State(false, "")
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