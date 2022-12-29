package com.example.rickmortycleanmvvm.model

sealed class ErrorType {
    object IsNetworkError : ErrorType()
    object IsQueryError : ErrorType()
}