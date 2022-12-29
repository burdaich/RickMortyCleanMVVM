package com.example.rickmortycleanmvvm.model.base

import com.example.rickmortycleanmvvm.model.ErrorType

abstract class BaseState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: ErrorType? = null
)