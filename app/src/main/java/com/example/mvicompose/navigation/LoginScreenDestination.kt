package com.example.mvicompose.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mvicompose.ui.feature.main.BaseAndroidViewModelFactory
import com.example.mvicompose.ui.feature.main.MainContract.Effect.Navigation
import com.example.mvicompose.ui.feature.main.MainViewModel
import com.example.mvicompose.ui.feature.main.composables.LoginScreen

@Composable
fun LoginScreenDestination(
    navController: NavController
) {
    val viewModel: MainViewModel = viewModel(factory = BaseAndroidViewModelFactory)

    LoginScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is Navigation.ToPreview -> navController.navigate(Route.CharactersList.toString())
                is Navigation.ToMoviesScreen -> navController.navigate(Route.Movies.toString())
            }
        }
    )
}