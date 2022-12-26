package com.example.rickmortycleanmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.uses_case.get_characters.GetCharactersByNameUsesCase
import com.example.domain.uses_case.get_characters.GetCharactersUsesCase
import com.example.rickmortycleanmvvm.model.CharactersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUsesCase: GetCharactersUsesCase,
    private val getCharactersUsesCaseByName: GetCharactersByNameUsesCase
) : ViewModel() {

    private val _state = MutableLiveData<CharactersState>()
    val state: LiveData<CharactersState> = _state

    init {
        getCharacters()
    }

    fun getCharacters() {
        getCharactersUsesCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _state.value = CharactersState(isLoading = true)
                is Resource.Success -> _state.value = CharactersState(characters = result.data)
                is Resource.Error -> _state.value =
                    CharactersState(error = result.message ?: "An unexpected error ocurred!")
            }
        }.launchIn(viewModelScope)
    }

    fun getCharactersByName(name: String) {
        getCharactersUsesCaseByName(name).onEach { result ->
            when (result) {
                is Resource.Loading -> _state.value = CharactersState(isLoading = true)
                is Resource.Success -> _state.value = CharactersState(characters = result.data)
                is Resource.Error -> _state.value =
                    CharactersState(error = result.message ?: "An unexpected error ocurred!")
            }
        }.launchIn(viewModelScope)
    }
}