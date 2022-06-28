package com.example.mvicompose.data.model

import androidx.annotation.DrawableRes
import com.example.mvicompose.R

data class Movie(
    val id: Long,
    var title: String,
    var overview: String,
    var releaseDate: String,
    @DrawableRes val posterDrawableResId: Int,
    val actorsDrawableResIds: List<Int> = listOf(
        R.drawable.tarantino,
        R.drawable.travolta,
        R.drawable.samuel,
        R.drawable.dicaprio,
        R.drawable.margot_robbie,
        R.drawable.matthew
    ),
    val voteAverage: Double,
)

fun buildMoviePreview() = Movie(
    id = 1,
    title = "Avengers",
    overview = "Matrix",
    releaseDate = "11.11.2011",
    posterDrawableResId = R.drawable.once_hollywood,
    voteAverage = 9.6,
)