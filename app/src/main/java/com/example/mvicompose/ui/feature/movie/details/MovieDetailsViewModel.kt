package com.example.mvicompose.ui.feature.movie.details

import android.content.Context
import androidx.biometric.BiometricPrompt
import com.example.mvicompose.common.CIPHERTEXT_WRAPPER
import com.example.mvicompose.common.SECRET_KEY_NAME
import com.example.mvicompose.common.SHARED_PREFS_FILENAME
import com.example.mvicompose.cryptography.CiphertextWrapper
import com.example.mvicompose.cryptography.CryptographyManagerImpl
import com.example.mvicompose.data.model.Movie
import com.example.mvicompose.ui.base.BaseViewModel

class MovieDetailsViewModel :
    BaseViewModel<MovieDetailsContract.Event, MovieDetailsContract.State, MovieDetailsContract.Effect>() {

    var movie: Movie? = null
        set(value) = setState { copy(movie = value) }

    private val cryptographyManager = CryptographyManagerImpl()

    override fun setInitialState() = MovieDetailsContract.State(movie)

    override fun handleEvents(event: MovieDetailsContract.Event) {
        when (event) {
            is MovieDetailsContract.Event.BuyButtonClicked -> buyButtonClicked(event.applicationContext)
            is MovieDetailsContract.Event.AuthViaBiometricSuccess -> {
                decryptCVVFromStorage(event.authenticationResult, event.applicationContext)
            }
        }

    }

    private fun buyButtonClicked(applicationContext: Context) {
        getCiphertextWrapperFromSharedPrefs(applicationContext)?.let { textWrapper ->
            setEffect {
                MovieDetailsContract.Effect.ShowBiometricPromptForDecryption(
                    BiometricPrompt.CryptoObject(
                        cryptographyManager.getInitializedCipherForDecryption(
                            SECRET_KEY_NAME,
                            textWrapper.initializationVector
                        )
                    )
                )
            }
        }
    }

    private fun decryptCVVFromStorage(
        authenticationResult: BiometricPrompt.AuthenticationResult,
        applicationContext: Context
    ) {
        getCiphertextWrapperFromSharedPrefs(applicationContext)?.let { textWrapper ->
            authenticationResult.cryptoObject?.cipher?.let {
                val cvv = cryptographyManager.decryptData(textWrapper.ciphertext, it)
                setEffect { MovieDetailsContract.Effect.SuccessTransaction }
            }
        }
    }

    private fun getCiphertextWrapperFromSharedPrefs(applicationContext: Context): CiphertextWrapper? {
        return cryptographyManager.getCiphertextWrapperFromSharedPrefs(
            applicationContext,
            SHARED_PREFS_FILENAME,
            Context.MODE_PRIVATE,
            CIPHERTEXT_WRAPPER
        )
    }
}