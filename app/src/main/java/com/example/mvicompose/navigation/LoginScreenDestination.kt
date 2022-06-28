package com.example.mvicompose.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mvicompose.ui.feature.login.LoginContract.Effect.Navigation
import com.example.mvicompose.ui.feature.login.LoginViewModel
import com.example.mvicompose.ui.feature.login.composables.LoginScreen

@Composable
fun LoginScreenDestination(
    navController: NavController
) {
    val viewModel: LoginViewModel = viewModel()

    LoginScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is Navigation.ToPreview -> navController.navigate(Route.Preview.toString())
                is Navigation.ToMoviesScreen -> navController.navigate(Route.Movies.toString())
            }
        }
    )
}