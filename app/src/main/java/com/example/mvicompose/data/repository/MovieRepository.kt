package com.example.mvicompose.data.repository

import com.example.mvicompose.data.model.Movie

interface MovieRepository {
    suspend fun getMovies(): List<Movie>
}