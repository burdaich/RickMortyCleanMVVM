package com.example.domain.uses_case.get_characters

import androidx.paging.PagingData
import com.example.domain.model.Character
import com.example.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUsesCase @Inject constructor(private val repository: CharactersRepository) {

     operator fun invoke(name: String? = null): Flow<PagingData<Character>> =
          repository.getCharacters(name)
}