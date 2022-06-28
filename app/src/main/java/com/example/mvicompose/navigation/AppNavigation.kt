package com.example.mvicompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mvicompose.common.navigate
import com.example.mvicompose.common.rememberGetData
import com.example.mvicompose.data.model.Movie

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Login.toString()
    ) {
        composable(Route.Login.toString()) {
            LoginScreenDestination(navController)
        }

        composable(Route.Preview.toString()) {
            PreviewScreenDestination(navController)
        }

        composable(Route.Movies.toString()) {
            MoviesScreenDestination(navController)
        }

        composable(
            route = Route.MovieDetails.toString(),
            arguments = listOf(
                navArgument(name = Route.MovieDetails.Key) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val movie = entry.rememberGetData<Movie>(key = Route.MovieDetails.Key)

            movie?.let {
                MovieDetailsScreenDestination(
                    movie = it,
                    navController = navController
                )
            }
        }
    }
}

sealed class Route(
    private val route: String,
    val Key: String = ""
) {
    object Login : Route(route = "login")
    object Preview : Route(route = "preview")
    object Movies : Route(route = "movies")
    object MovieDetails : Route(route = "movie_details", Key = "movie")

    override fun toString(): String {
        return when {
            Key.isNotEmpty() -> "$route/{$Key}"
            else -> route
        }
    }
}

fun NavController.navigateToMovieDetails(movie: Movie) {
    navigate(
        route = Route.MovieDetails.toString(),
        data = Route.MovieDetails.Key to movie
    )
}