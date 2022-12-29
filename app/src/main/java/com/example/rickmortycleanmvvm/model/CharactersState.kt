package com.example.rickmortycleanmvvm.model

import com.example.domain.model.Characters
import com.example.rickmortycleanmvvm.model.base.BaseState

class CharactersState(
     isLoading: Boolean = false,  characters: Characters? = null,  error: ErrorType? = null
): BaseState<Characters>(isLoading, characters, error)