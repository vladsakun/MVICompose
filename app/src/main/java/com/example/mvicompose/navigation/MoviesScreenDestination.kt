package com.example.mvicompose.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mvicompose.ui.feature.movie.list.MoviesContract
import com.example.mvicompose.ui.feature.movie.list.MoviesListViewModel
import com.example.mvicompose.ui.feature.movie.list.composables.MoviesScreen

@Composable
fun MoviesScreenDestination(navController: NavController) {
    val viewModel: MoviesListViewModel = viewModel()
    MoviesScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is MoviesContract.Effect.Navigation.ToMovieDetails) {
                navController.navigateToMovieDetails(navigationEffect.movie)
            }
        })
}