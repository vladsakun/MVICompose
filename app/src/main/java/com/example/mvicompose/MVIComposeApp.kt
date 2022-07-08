package com.example.mvicompose

import android.app.Application
import com.example.mvicompose.ui.feature.main.BaseAndroidViewModelFactory

class MVIComposeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        BaseAndroidViewModelFactory.inject(this)
    }

    companion object {
        init {
            System.loadLibrary("mvicompose")
        }
    }
}