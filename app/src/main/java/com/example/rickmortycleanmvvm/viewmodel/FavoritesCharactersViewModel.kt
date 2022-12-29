package com.example.rickmortycleanmvvm.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.uses_case.get_characters.GetFavoritesCharactersUsesCase
import com.example.rickmortycleanmvvm.model.FavoritesCharactersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesCharactersViewModel @Inject constructor(private val getFavoritesCharactersUsesCase: GetFavoritesCharactersUsesCase) :
    BaseViewModel() {
    private val _state = MutableSharedFlow<FavoritesCharactersState>()
    val state: SharedFlow<FavoritesCharactersState> = _state

    fun getFavoritesCharacters() {
        viewModelScope.launch {
            getFavoritesCharactersUsesCase().collect { result ->
                when (result) {
                    is Resource.Loading -> _state.emit(FavoritesCharactersState(isLoading = true))
                    is Resource.Success -> _state.emit(
                        FavoritesCharactersState(characters = result.data)
                    )
                    is Resource.Error -> _state.emit(
                        FavoritesCharactersState(
                            error = getErrorType(result)
                        )
                    )
                }
            }
        }
    }
}