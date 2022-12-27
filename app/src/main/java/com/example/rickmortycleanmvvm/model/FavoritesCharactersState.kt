package com.example.rickmortycleanmvvm.model

import com.example.domain.model.Character

data class FavoritesCharactersState(
    val isLoading: Boolean = false, val characters: List<Character>? = null, val error: String = ""
)