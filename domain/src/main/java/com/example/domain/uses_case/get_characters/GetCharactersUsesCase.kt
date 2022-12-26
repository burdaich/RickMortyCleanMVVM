package com.example.domain.uses_case.get_characters

import android.util.Log
import com.example.domain.common.Resource
import com.example.domain.repository.CharactersRepository
import javax.inject.Inject
import com.example.domain.model.Characters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetCharactersUsesCase @Inject constructor(private val repository: CharactersRepository) {

    operator fun invoke(): Flow<Resource<Characters>> = flow {
        try {
            emit(Resource.Loading())
            val characters = repository.getCharacters()
            emit(Resource.Success(characters))
        } catch (e: Exception) {
            throw (e)
            emit(Resource.Error("Couldn´t  retrieve data. Check your internet connection"))
        }
    }
}