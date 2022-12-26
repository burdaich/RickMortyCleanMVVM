package com.example.domain.repository

import com.example.domain.model.Characters

interface CharactersRepository {
    suspend fun getCharacters(): Characters
    suspend fun getCharactersByName(name : String): Characters
}