package com.example.mvicompose.ui.feature.movie.list

import com.example.mvicompose.data.model.Movie
import com.example.mvicompose.ui.base.ViewEvent
import com.example.mvicompose.ui.base.ViewSideEffect
import com.example.mvicompose.ui.base.ViewState

class MoviesContract {

    sealed class Event : ViewEvent {
        object Retry : Event()
        data class MovieSelection(val movie: Movie) : Event()
    }

    data class State(
        val movies: List<Movie>,
        val isLoading: Boolean,
        val isError: Boolean
    ) : ViewState {

        companion object {
            fun getDefaultState() = State(emptyList(), isLoading = false, isError = false)
        }
    }

    sealed class Effect : ViewSideEffect {
        object DataWasLoaded : Effect()

        sealed class Navigation : Effect() {
            data class ToMovieDetails(val movie: Movie) : Navigation()
        }
    }
}