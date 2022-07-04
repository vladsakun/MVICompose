package com.example.mvicompose.cryptography

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import com.example.mvicompose.common.ANDROID_KEY_STORE
import com.squareup.moshi.Moshi
import java.nio.charset.Charset
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class CryptographyManagerImpl : CryptographyManager {

    override fun getInitializedCipherForEncryption(keyName: String): Cipher {
        val cipher = getCipher()
        val secretKey = getOrCreateSecretKey(keyName)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher
    }

    override fun getInitializedCipherForDecryption(
        keyName: String,
        initializationVector: ByteArray
    ): Cipher {
        val cipher = getCipher()
        val secretKey = getOrCreateSecretKey(keyName)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, initializationVector))
        return cipher
    }

    override fun encryptData(plainText: String, cipher: Cipher): CiphertextWrapper {
        val ciphertext = cipher.doFinal(plainText.toByteArray(Charset.forName("UTF-8")))
        return CiphertextWrapper(ciphertext, cipher.iv)
    }

    override fun decryptData(ciphertext: ByteArray, cipher: Cipher): String {
        val plainText = cipher.doFinal(ciphertext)
        return String(plainText, Charset.forName("UTF-8"))
    }

    private fun getCipher(): Cipher {
        val transformation = "$ENCYPTION_ALGORITHM/$ENCYPTION_BLOCK_MODE/$ENCYPTION_PADDING"
        return Cipher.getInstance(transformation)
    }

    private fun getOrCreateSecretKey(keyName: String): SecretKey {
        // If SecretKey was previously created for that keyName, then grab and return it.
        val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
        keyStore.load(null) // Keystore must be loaded before it can be accessed
        keyStore.getKey(keyName, null)?.let { return it as SecretKey }

        val paramsBuilder = KeyGenParameterSpec.Builder(
            keyName,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
        paramsBuilder.apply {
            setBlockModes(ENCYPTION_BLOCK_MODE)
            setEncryptionPaddings(ENCYPTION_PADDING)
            setKeySize(getKeySize())
            setUserAuthenticationRequired(false)
        }

        Log.d(TAG, "getOrCreateSecretKey: ${getKeySize()}")

        val keyGenParams = paramsBuilder.build()
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEYSTORE
        )
        keyGenerator.init(keyGenParams)
        return keyGenerator.generateKey()
    }

    override fun persistCiphertextWrapperToSharedPrefs(
        ciphertextWrapper: CiphertextWrapper,
        context: Context,
        filename: String,
        mode: Int,
        prefKey: String
    ) {
        val json = Moshi.Builder()
            .build()
            .adapter(CiphertextWrapper::class.java)
            .toJson(ciphertextWrapper)
        context.getSharedPreferences(filename, mode).edit().putString(prefKey, json).apply()
    }

    override fun getCiphertextWrapperFromSharedPrefs(
        context: Context,
        filename: String,
        mode: Int,
        prefKey: String
    ): CiphertextWrapper? {
        val json = context.getSharedPreferences(filename, mode).getString(prefKey, null)
        return json?.let {
            Moshi.Builder().build().adapter(CiphertextWrapper::class.java).fromJson(it)
        }
    }

    private external fun getKeySize(): Int

    companion object {
        private const val TAG = "CryptographyManagerImpl"

        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val ENCYPTION_BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
        private const val ENCYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
        private const val ENCYPTION_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES

        init {
            System.loadLibrary("mvicompose")
        }
    }
}