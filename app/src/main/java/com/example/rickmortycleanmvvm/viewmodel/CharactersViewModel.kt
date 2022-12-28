package com.example.rickmortycleanmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.domain.model.Character
import com.example.domain.uses_case.get_characters.GetCharactersUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUsesCase: GetCharactersUsesCase
) : ViewModel() {

    private var _state: MutableSharedFlow<PagingData<Character>> = MutableSharedFlow()
    var state: SharedFlow<PagingData<Character>> = _state

    fun getCharactersByName(name: String? = null) {
        viewModelScope.launch {
            getCharactersUsesCase(name).collect {
                _state.emit(it)
            }
        }
    }
}