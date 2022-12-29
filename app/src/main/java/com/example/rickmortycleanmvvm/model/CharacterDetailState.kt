package com.example.rickmortycleanmvvm.model

import com.example.domain.model.Character
import com.example.rickmortycleanmvvm.model.base.BaseState

class CharacterDetailState(
    isLoading: Boolean = false,
    character: Character? = null,
    val isUpdated: Boolean = false,
    error: ErrorType? = null
) : BaseState<Character>(isLoading, character, error)