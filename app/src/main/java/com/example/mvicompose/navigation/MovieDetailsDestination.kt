package com.example.mvicompose.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mvicompose.data.model.Movie
import com.example.mvicompose.ui.feature.movie.details.MovieDetailsViewModel
import com.example.mvicompose.ui.feature.movie.details.composables.MovieDetailsScreen

@Composable
fun MovieDetailsScreenDestination(movie: Movie, navController: NavController) {
    val viewModel: MovieDetailsViewModel = viewModel()
    viewModel.movie = movie

    MovieDetailsScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) }
    )
}