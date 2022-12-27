package com.example.rickmortycleanmvvm.model

import com.example.domain.model.Character

data class CharacterDetailState(
    val isLoading: Boolean = false,
    val character: Character? = null,
    val isUpdated: Boolean = false,
    val error: String = ""
)