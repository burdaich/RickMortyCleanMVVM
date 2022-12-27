package com.example.domain.uses_case.get_characters

import com.example.domain.common.Resource
import com.example.domain.model.Characters
import com.example.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCharactersByNameUsesCase @Inject constructor(private val repository: CharactersRepository) {

    operator fun invoke(name: String): Flow<Resource<Characters>> = flow {
        try {
            emit(Resource.Loading())
            val characters = repository.getCharactersByName(name)
            emit(Resource.Success(characters))
        } catch (e: Exception) {
            throw (e)
            emit(Resource.Error("Couldn´t  retrieve data. Check your internet connection"))
        }
    }
}