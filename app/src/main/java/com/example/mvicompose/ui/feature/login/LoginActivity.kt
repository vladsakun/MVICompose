package com.example.mvicompose.ui.feature.login

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import com.example.mvicompose.navigation.AppNavigation
import com.example.mvicompose.ui.theme.MVIComposeTheme

class LoginActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVIComposeTheme {
                AppNavigation()
            }
        }
    }
}