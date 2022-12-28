package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.api.Api
import com.example.data.database.databases.CharacterDatabase
import com.example.data.extensions.characterEntityToCharacters
import com.example.data.extensions.characterModelToCharacters
import com.example.data.extensions.toCharacter
import com.example.data.extensions.toCharacterEntity
import com.example.data.paging.CharactersPagingSource
import com.example.domain.model.Character
import com.example.domain.model.Characters
import com.example.domain.repository.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val api: Api,
    private val characterDatabase: CharacterDatabase
) : CharactersRepository {
    override fun getCharacters(name: String?) = Pager(
        config = PagingConfig(pageSize = 1, enablePlaceholders = false),
        pagingSourceFactory = { CharactersPagingSource(api, name) }).flow

    override suspend fun addCharacterToFavorite(character: Character): Long {
        return characterDatabase.characterDao().insert(character.toCharacterEntity())
    }

    override suspend fun getCharactersFavorite(apiId: Int): Character? {
        return characterDatabase.characterDao().getFavoriteCharacter(apiId)?.toCharacter()
    }

    override suspend fun getFavoritesCharacters(): List<Character> {
        return characterDatabase.characterDao().getFavoritesCharacters()
            .characterEntityToCharacters()
    }

    override suspend fun deleteFromFavorites(apiId: Int): Int {
        return characterDatabase.characterDao().deleteFromFavorites(apiId)
    }
}