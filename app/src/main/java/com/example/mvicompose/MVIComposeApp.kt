package com.example.mvicompose

import android.app.Application
import android.provider.Settings
import android.util.Log
import com.example.mvicompose.ui.feature.main.BaseAndroidViewModelFactory

class MVIComposeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        BaseAndroidViewModelFactory.inject(this)

        val mSystemScreenOffTimeOut = Settings.System.getInt(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT)

        Log.d(TAG, "onCreate: mSystemScreenOffTimeOut: $mSystemScreenOffTimeOut")
    }

    companion object {
        private const val TAG = "MVIComposeApp"

        init {
            System.loadLibrary("mvicompose")
        }
    }
}