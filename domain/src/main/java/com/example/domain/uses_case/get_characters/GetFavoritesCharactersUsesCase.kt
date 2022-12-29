package com.example.domain.uses_case.get_characters

import com.example.domain.common.Resource
import com.example.domain.model.Character
import com.example.domain.repository.CharactersRepository
import com.example.domain.uses_case.BaseUsesCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoritesCharactersUsesCase @Inject constructor(private val repository: CharactersRepository) :
    BaseUsesCase() {

    operator fun invoke(): Flow<Resource<List<Character>>> =
        flow {
            emit(Resource.Loading())
            val characters = safeCall { repository.getFavoritesCharacters() }
            emit(characters)
        }
}