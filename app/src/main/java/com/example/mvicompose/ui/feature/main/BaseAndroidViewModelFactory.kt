package com.example.mvicompose.ui.feature.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object BaseAndroidViewModelFactory : ViewModelProvider.Factory {

    private lateinit var application: Application

    fun inject(application: Application) {
        BaseAndroidViewModelFactory.application = application
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Application::class.java).newInstance(application)
    }
}