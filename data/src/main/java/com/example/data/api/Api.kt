package com.example.data.api

import com.example.data.model.CharactersModel
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharactersModel

    @GET("character")
    suspend fun getCharactersByName(@Query("name") name: String): CharactersModel
}