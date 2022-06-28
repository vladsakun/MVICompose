package com.example.mvicompose.data.repository

import com.example.mvicompose.R
import com.example.mvicompose.data.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class MovieRepositoryImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieRepository {

    override suspend fun getMovies(): List<Movie> {
        delay(1000)
        return listOf(
            Movie(
                id = 1,
                title = "Pulp fiction",
                overview = "In the realm of underworld, a series of incidents intertwines the lives of two Los Angeles mobsters, a gangster's wife, a boxer and two small-time criminals.",
                releaseDate = "12.02.1994",
                posterDrawableResId = R.drawable.pulp_fiction,
                voteAverage = 9.9,
            ),
            Movie(
                id = 2,
                title = "Fighting club",
                overview = "Unhappy with his capitalistic lifestyle, a white-collared insomniac forms an underground fight club with Tyler, a careless soap salesman. Soon, their venture spirals down into something sinister.",
                releaseDate = "14.08.1999",
                posterDrawableResId = R.drawable.fight_club,
                voteAverage = 9.2,
            ),
            Movie(
                id = 3,
                title = "The Hateful Eight",
                overview = "A bounty hunter and his captured fugitive are caught in the middle of a snowstorm. They seek refuge at a small lodge and encounter a twisted turn of events there.",
                releaseDate = "05.05.2015",
                posterDrawableResId = R.drawable.hateful_eight,
                voteAverage = 9.1,
            ),
            Movie(
                id = 4,
                title = "Once upon a time in hollywood",
                overview = "Rick, a washed-out actor, and Cliff, his stunt double, struggle to recapture fame and success in 1960s Los Angeles. Meanwhile, living next door to Rick is Sharon Tate and her husband Roman Polanski.",
                releaseDate = "26.07.2019",
                posterDrawableResId = R.drawable.once_hollywood,
                voteAverage = 9.5,
            ),
            Movie(
                id = 5,
                title = "One Flew Over The Cuckoo's Nest",
                overview = "In order to escape the prison labour, McMurphy, a prisoner, fakes insanity and is shifted to the special ward for the mentally unstable. In this ward, he must rise up against a cruel nurse, Ratched.",
                releaseDate = "19.11.1875",
                posterDrawableResId = R.drawable.nest,
                voteAverage = 9.8,
            )
        )
    }
}