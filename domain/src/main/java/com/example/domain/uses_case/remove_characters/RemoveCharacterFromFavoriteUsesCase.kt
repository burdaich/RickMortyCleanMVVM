package com.example.domain.uses_case.remove_characters

import com.example.domain.common.Resource
import com.example.domain.model.Character
import com.example.domain.repository.CharactersRepository
import com.example.domain.uses_case.BaseUsesCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveCharacterFromFavoriteUsesCase @Inject constructor(private val repository: CharactersRepository) :
    BaseUsesCase() {
    operator fun invoke(character: Character): Flow<Resource<Long>> = flow {
        emit(Resource.Loading())
        val response = safeCall { repository.deleteFromFavorites(character.id).toLong() }
        emit(response)
    }
}