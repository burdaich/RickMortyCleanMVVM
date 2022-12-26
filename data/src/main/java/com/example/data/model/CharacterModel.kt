package com.example.data.model

data class CharacterModel(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val name: String,
    val status: String,
    val species: String,
    val url: String
)