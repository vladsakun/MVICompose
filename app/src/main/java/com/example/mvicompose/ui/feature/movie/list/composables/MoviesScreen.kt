package com.example.mvicompose.ui.feature.movie.list.composables

import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.example.mvicompose.R
import com.example.mvicompose.ui.base.SIDE_EFFECTS_KEY
import com.example.mvicompose.ui.common.NetworkError
import com.example.mvicompose.ui.common.Progress
import com.example.mvicompose.ui.feature.movie.list.MoviesContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun MoviesScreen(
    state: MoviesContract.State,
    effectFlow: Flow<MoviesContract.Effect>?,
    onEventSent: (event: MoviesContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: MoviesContract.Effect.Navigation) -> Unit
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val snackBarMessage = stringResource(id = R.string.movies_loaded)

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is MoviesContract.Effect.DataWasLoaded -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = snackBarMessage,
                        duration = SnackbarDuration.Short
                    )
                }
                is MoviesContract.Effect.Navigation.ToMovieDetails -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { MoviesTopBar() }
    ) {
        when {
            state.isLoading -> Progress()
            state.isError -> NetworkError { onEventSent(MoviesContract.Event.Retry) }
            else -> MoviesList(movies = state.movies) {
                onEventSent(MoviesContract.Event.MovieSelection(it))
            }
        }
    }
}