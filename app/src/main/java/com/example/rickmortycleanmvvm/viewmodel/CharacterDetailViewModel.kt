package com.example.rickmortycleanmvvm.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.model.Character
import com.example.domain.uses_case.add_characters.AddCharacterToFavoriteUsesCase
import com.example.domain.uses_case.get_characters.GetCharacterFavoriteByApiIdUsesCase
import com.example.domain.uses_case.remove_characters.RemoveCharacterFromFavoriteUsesCase
import com.example.rickmortycleanmvvm.model.CharacterDetailState
import com.example.rickmortycleanmvvm.model.ErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val addCharacterToFavoriteUsesCase: AddCharacterToFavoriteUsesCase,
    private val getCharacterFavoriteByApiIdUsesCase: GetCharacterFavoriteByApiIdUsesCase,
    private val removeCharacterFromFavoriteUsesCase: RemoveCharacterFromFavoriteUsesCase
) :
    BaseViewModel() {

    private val _state = MutableSharedFlow<CharacterDetailState>()
    val state: SharedFlow<CharacterDetailState> = _state

    fun getCharacterFavoriteByApiId(apiId: Int) {
        viewModelScope.launch {
            getCharacterFavoriteByApiIdUsesCase(apiId).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.emit(CharacterDetailState(isLoading = true))
                    is Resource.Success -> {
                        _state.emit(CharacterDetailState(character = result.data))
                    }
                    is Resource.Error -> _state.emit(
                        CharacterDetailState(
                            error = getErrorType(result)
                        )
                    )
                }
            }
        }
    }

    private fun addCharacterToFavorite(character: Character) {
        viewModelScope.launch {
            addCharacterToFavoriteUsesCase(character).collect { result ->
                manageCharacterUpdates(result, character)
            }
        }
    }

    private fun removeCharacterFromFavorite(character: Character) {
        viewModelScope.launch {
            removeCharacterFromFavoriteUsesCase(character).collect { result ->
                manageCharacterUpdates(result, character)
            }
        }
    }

    private suspend fun manageCharacterUpdates(
        result: Resource<Long>,
        character: Character
    ) {
        when (result) {
            is Resource.Loading -> _state.emit(CharacterDetailState(isLoading = true))
            is Resource.Success -> {
                result.data?.let { data ->
                    if (data > NO_ROWS_AFFECTED) {
                        character.isFavorite = !character.isFavorite
                        _state.emit(
                            CharacterDetailState(character = character, isUpdated = true)
                        )
                    } else {
                        _state.emit(CharacterDetailState(error = ErrorType.IsQueryError))
                    }
                }
            }
            is Resource.Error -> _state.emit(
                CharacterDetailState(error = getErrorType(result))
            )
        }
    }

    fun manageCharacter(character: Character) {
        if (character.isFavorite) removeCharacterFromFavorite(character) else addCharacterToFavorite(
            character
        )
    }

    companion object {
        private const val NO_ROWS_AFFECTED = 0
    }
}