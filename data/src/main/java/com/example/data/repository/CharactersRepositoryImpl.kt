package com.example.data.repository

import com.example.data.api.Api
import com.example.data.extensions.toCharacters
import com.example.domain.model.Characters
import com.example.domain.repository.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(private val api: Api) : CharactersRepository {
    override suspend fun getCharacters(): Characters {
        return api.getCharacters().toCharacters()
    }

    override suspend fun getCharactersByName(name: String): Characters {
        return api.getCharactersByName(name).toCharacters()
    }
}