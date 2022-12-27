package com.example.domain.model

data class Character(
    val gender: String,
    val id: Int,
    val image: String,
    val name: String,
    val status: String,
    val species: String,
    var isFavorite: Boolean = false
)
