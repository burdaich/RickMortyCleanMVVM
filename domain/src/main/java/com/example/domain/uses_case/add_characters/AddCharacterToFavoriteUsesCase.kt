package com.example.domain.uses_case.add_characters

import com.example.domain.common.Resource
import com.example.domain.model.Character
import com.example.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddCharacterToFavoriteUsesCase @Inject constructor(private val repository: CharactersRepository) {

    operator fun invoke(character: Character): Flow<Resource<Character>> = flow {
        try {
            emit(Resource.Loading())
            repository.addCharacterToFavorite(character)
            character.isFavorite = true
            emit(Resource.Success(character))
        } catch (e: Exception) {
            throw (e)
            emit(Resource.Error("Couldn´t  retrieve data. Check your internet connection"))
        }
    }
}