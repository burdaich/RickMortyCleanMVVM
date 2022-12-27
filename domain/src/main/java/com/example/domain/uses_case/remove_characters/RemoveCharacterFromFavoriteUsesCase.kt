package com.example.domain.uses_case.remove_characters

import com.example.domain.common.Resource
import com.example.domain.model.Character
import com.example.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveCharacterFromFavoriteUsesCase @Inject constructor(private val repository: CharactersRepository) {
    operator fun invoke(character: Character): Flow<Resource<Character>> = flow {
        try {
            emit(Resource.Loading())
            val affectedRows = repository.deleteFromFavorites(character.id)
            if (affectedRows > NO_ROWS_AFFECTED) {
                character.isFavorite = false
                emit(Resource.Success(character))
            } else {
                emit(Resource.Error("Error deleting element"))
            }
        } catch (e: Exception) {
            throw (e)
            emit(Resource.Error("Couldn´t  retrieve data. Check your internet connection"))
        }
    }

    companion object {
        private const val NO_ROWS_AFFECTED = 0
    }
}