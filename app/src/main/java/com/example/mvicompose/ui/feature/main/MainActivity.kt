package com.example.mvicompose.ui.feature.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import com.example.mvicompose.navigation.AppNavigation
import com.example.mvicompose.ui.theme.MVIComposeTheme

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVIComposeTheme {
                AppNavigation()
            }
        }
    }
}