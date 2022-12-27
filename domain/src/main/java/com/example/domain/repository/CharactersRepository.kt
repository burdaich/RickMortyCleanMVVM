package com.example.domain.repository

import com.example.domain.model.Character
import com.example.domain.model.Characters

interface CharactersRepository {
    suspend fun getCharacters(): Characters
    suspend fun getCharactersByName(name: String): Characters
    suspend fun addCharacterToFavorite(character: Character): Long
    suspend fun getCharactersFavorite(apiId: Int): Character?
    suspend fun getFavoritesCharacters(): List<Character>
    suspend fun deleteFromFavorites(apiId: Int): Int
}