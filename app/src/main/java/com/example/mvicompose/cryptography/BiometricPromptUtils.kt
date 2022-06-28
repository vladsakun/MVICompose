package com.example.mvicompose.cryptography

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.mvicompose.R
import com.example.mvicompose.common.getActivity

object BiometricPromptUtils {

    fun createBiometricPrompt(
        context: Context,
        processSuccess: (BiometricPrompt.AuthenticationResult) -> Unit
    ): BiometricPrompt? {
        return context.getActivity<FragmentActivity>()?.let { activity ->
            val executor = ContextCompat.getMainExecutor(context)
            val callback = object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    processSuccess(result)
                }
            }

            BiometricPrompt(activity, executor, callback)
        }
    }

    fun createPromptInfo(context: Context): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle(context.getString(R.string.biometrics_dialog_title))
            .setSubtitle(context.getString(R.string.biometrics_dialog_subtitle))
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()
    }


    fun getEnrollBiometricsIntent(): Intent {
        // Prompts the user to create credentials that your app accepts.
        val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
            putExtra(
                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BiometricManager.Authenticators.BIOMETRIC_STRONG
            )
        }
        return enrollIntent
    }
}