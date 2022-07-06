package com.example.mvicompose.data.repository

import com.apollographql.apollo.api.Response
import com.example.mvicompose.CharacterQuery
import com.example.mvicompose.CharactersListQuery
import com.example.mvicompose.data.model.Movie

interface MainRepository {
    suspend fun getMovies(): List<Movie>
    suspend fun getCharacters(): Response<CharactersListQuery.Data>
    suspend fun getCharacter(id: String): Response<CharacterQuery.Data>
}