package com.example.rickmortycleanmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.model.Character
import com.example.domain.uses_case.add_characters.AddCharacterToFavoriteUsesCase
import com.example.domain.uses_case.get_characters.GetCharacterFavoriteByApiIdUsesCase
import com.example.domain.uses_case.remove_characters.RemoveCharacterFromFavoriteUsesCase
import com.example.rickmortycleanmvvm.model.CharacterDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val addCharacterToFavoriteUsesCase: AddCharacterToFavoriteUsesCase,
    private val getCharacterFavoriteByApiIdUsesCase: GetCharacterFavoriteByApiIdUsesCase,
    private val removeCharacterFromFavoriteUsesCase: RemoveCharacterFromFavoriteUsesCase
) :
    ViewModel() {

    private val _state = MutableLiveData<CharacterDetailState>()
    val state: LiveData<CharacterDetailState> = _state

    fun getCharacterFavoriteByApiId(apiId: Int) {
        getCharacterFavoriteByApiIdUsesCase(apiId).onEach { result ->
            when (result) {
                is Resource.Loading -> _state.value = CharacterDetailState(isLoading = true)
                is Resource.Success -> {
                    _state.value = CharacterDetailState(character = result.data)
                }
                is Resource.Error -> _state.value =
                    CharacterDetailState(error = result.message ?: "An unexpected error ocurred!")
            }
        }.launchIn(viewModelScope)
    }

    private fun addCharacterToFavorite(character: Character) {
        addCharacterToFavoriteUsesCase(character).onEach { result ->
            manageCharacterUpdates(result, character)
        }.launchIn(viewModelScope)
    }

    private fun removeCharacterFromFavorite(character: Character) {
        removeCharacterFromFavoriteUsesCase(character).onEach { result ->
            manageCharacterUpdates(result, character)
        }.launchIn(viewModelScope)
    }

    private fun manageCharacterUpdates(
        result: Resource<Character>,
        character: Character
    ) {
        when (result) {
            is Resource.Loading -> _state.value = CharacterDetailState(isLoading = true)
            is Resource.Success -> {
                _state.value = CharacterDetailState(character = character, isUpdated = true)
            }
            is Resource.Error -> _state.value =
                CharacterDetailState(error = result.message ?: "An unexpected error ocurred!")
        }
    }

    fun manageCharacter(character: Character) {
        if (character.isFavorite) removeCharacterFromFavorite(character) else addCharacterToFavorite(
            character
        )
    }
}