package com.example.mvicompose.ui.feature.movie.details

import android.app.Application
import android.content.Context
import androidx.biometric.BiometricPrompt
import com.example.mvicompose.common.CIPHERTEXT_WRAPPER
import com.example.mvicompose.common.SECRET_KEY_NAME
import com.example.mvicompose.common.SHARED_PREFS_FILENAME
import com.example.mvicompose.cryptography.CiphertextWrapper
import com.example.mvicompose.cryptography.CryptographyManagerImpl
import com.example.mvicompose.data.model.Movie
import com.example.mvicompose.ui.base.BaseAndroidViewModel

class MovieDetailsViewModel(application: Application) :
    BaseAndroidViewModel<MovieDetailsContract.Event, MovieDetailsContract.State, MovieDetailsContract.Effect>(
        application
    ) {

    var movie: Movie? = null
        set(value) = setState { copy(movie = value) }

    private val cryptographyManager = CryptographyManagerImpl()

    override fun setInitialState() = MovieDetailsContract.State(movie)

    override fun handleEvents(event: MovieDetailsContract.Event) {
        when (event) {
            is MovieDetailsContract.Event.BuyButtonClicked -> buyButtonClicked()
            is MovieDetailsContract.Event.AuthViaBiometricSuccess -> decryptCVVFromStorage(event.authenticationResult)
        }
    }

    private fun buyButtonClicked() {
        getCiphertextWrapperFromSharedPrefs()?.let { textWrapper ->
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

    private fun decryptCVVFromStorage(authenticationResult: BiometricPrompt.AuthenticationResult) {
        getCiphertextWrapperFromSharedPrefs()?.let { textWrapper ->
            authenticationResult.cryptoObject?.cipher?.let {
                val cvv = cryptographyManager.decryptData(textWrapper.ciphertext, it)
                setEffect { MovieDetailsContract.Effect.SuccessTransaction }
            }
        }
    }

    private fun getCiphertextWrapperFromSharedPrefs(): CiphertextWrapper? {
        return cryptographyManager.getCiphertextWrapperFromSharedPrefs(
            getApplication(),
            SHARED_PREFS_FILENAME,
            Context.MODE_PRIVATE,
            CIPHERTEXT_WRAPPER
        )
    }
}