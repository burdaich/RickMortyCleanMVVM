package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getCharacters(name: String? = null): Flow<PagingData<Character>>
    suspend fun addCharacterToFavorite(character: Character): Long
    suspend fun getCharactersFavorite(apiId: Int): Character?
    suspend fun getFavoritesCharacters(): List<Character>
    suspend fun deleteFromFavorites(apiId: Int): Int
}