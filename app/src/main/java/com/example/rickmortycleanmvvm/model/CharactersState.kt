package com.example.rickmortycleanmvvm.model

import com.example.domain.model.Characters

data class CharactersState(
    val isLoading: Boolean = false, val characters: Characters? = null, val error: String = ""
)