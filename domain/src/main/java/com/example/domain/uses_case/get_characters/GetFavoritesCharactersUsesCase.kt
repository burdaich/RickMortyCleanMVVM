package com.example.domain.uses_case.get_characters

import com.example.domain.common.Resource
import com.example.domain.model.Character
import com.example.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoritesCharactersUsesCase @Inject constructor(private val repository: CharactersRepository) {

    operator fun invoke(): Flow<Resource<List<Character>>> =
        flow {
            try {
                emit(Resource.Loading())
                val characters = repository.getFavoritesCharacters()
                emit(Resource.Success(characters))
            } catch (e: Exception) {
                throw (e)
                emit(Resource.Error("Couldn´t  retrieve data. Check your internet connection"))
            }
        }
}