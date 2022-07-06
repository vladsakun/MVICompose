package com.example.mvicompose.ui.feature.movie.list

import androidx.lifecycle.viewModelScope
import com.example.mvicompose.data.repository.MainRepositoryImpl
import com.example.mvicompose.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MoviesListViewModel :
    BaseViewModel<MoviesContract.Event, MoviesContract.State, MoviesContract.Effect>() {

    private var isError = true

    init {
        getMovies()
    }

    override fun setInitialState() = MoviesContract.State.getDefaultState()

    override fun handleEvents(event: MoviesContract.Event) {
        when (event) {
            is MoviesContract.Event.MovieSelection -> setEffect {
                MoviesContract.Effect.Navigation.ToMovieDetails(event.movie)
            }
            is MoviesContract.Event.Retry -> getMovies()
        }
    }

    // TODO Extract to a reducer
    private fun getMovies() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            val movies = MainRepositoryImpl.getMovies()

            if (isError) {
                setState { copy(isError = true, isLoading = false) }
                isError = false
            } else {
                setState { copy(movies = movies, isLoading = false) }
            }
        }
    }
}