package com.example.rickmortycleanmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.uses_case.get_characters.GetFavoritesCharactersUsesCase
import com.example.rickmortycleanmvvm.model.FavoritesCharactersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoritesCharactersViewModel @Inject constructor(private val getFavoritesCharactersUsesCase: GetFavoritesCharactersUsesCase) :
    ViewModel() {
    private val _state = MutableLiveData<FavoritesCharactersState>()
    val state: LiveData<FavoritesCharactersState> = _state

    fun getFavoritesCharacters() {
        getFavoritesCharactersUsesCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _state.value = FavoritesCharactersState(isLoading = true)
                is Resource.Success -> _state.value =
                    FavoritesCharactersState(characters = result.data)
                is Resource.Error -> _state.value =
                    FavoritesCharactersState(
                        error = result.message ?: "An unexpected error ocurred!"
                    )
            }
        }.launchIn(viewModelScope)
    }
}