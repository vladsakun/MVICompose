package com.example.mvicompose.ui.feature.movie.details

import android.content.Context
import androidx.biometric.BiometricPrompt
import com.example.mvicompose.data.model.Movie
import com.example.mvicompose.ui.base.ViewEvent
import com.example.mvicompose.ui.base.ViewSideEffect
import com.example.mvicompose.ui.base.ViewState

class MovieDetailsContract {
    sealed class Event : ViewEvent {
        class BuyButtonClicked(val applicationContext: Context) : Event()
        class AuthViaBiometricSuccess(
            val authenticationResult: BiometricPrompt.AuthenticationResult,
            val applicationContext: Context
        ) : Event()
    }

    data class State(
        val movie: Movie? = null
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        class ShowBiometricPromptForDecryption(
            val cryptoObject: BiometricPrompt.CryptoObject
        ) :
            Effect()

        object SuccessTransaction : Effect()
    }
}