package com.example.mvicompose.ui.feature.movie.list.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mvicompose.data.model.Movie
import com.example.mvicompose.data.model.buildMoviePreview

@Composable
fun MoviesList(
    movies: List<Movie>,
    onItemClick: (Movie) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            item {
                MoviesListHeader()
            }
            items(movies) { movie ->
                MoviesListItem(movie = movie, onItemClick = onItemClick)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesListPreview() {
    val movies = List(3) { buildMoviePreview() }
    MoviesList(movies = movies) {}
}