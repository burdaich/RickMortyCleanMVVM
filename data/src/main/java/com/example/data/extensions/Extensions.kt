package com.example.data.extensions

import com.example.data.database.entities.CharacterEntity
import com.example.data.model.CharacterModel
import com.example.data.model.CharactersInfoModel
import com.example.data.model.CharactersModel
import com.example.domain.model.Character
import com.example.domain.model.Characters
import com.example.domain.model.CharactersInfo

fun CharactersModel.characterModelToCharacters(): Characters {
    return Characters(
        info = info.toCharactersInfo(),
        characters = results.characterModelToCharacters()
    )
}

fun CharactersInfoModel.toCharactersInfo(): CharactersInfo {
    return CharactersInfo(count, next, pages, prev)
}

fun CharacterModel.toCharacter(): Character {
    return Character(
        gender = gender,
        id = id,
        image = image,
        name = name,
        status = status,
        species = species
    )
}

fun List<CharacterModel>.characterModelToCharacters(): List<Character> {
    return map { it.toCharacter() }
}

fun Character.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(id, image, name, status, species, gender)
}

fun CharacterEntity.toCharacter(): Character {
    return Character(
        gender = gender,
        id = apiId,
        image = image,
        name = name,
        status = status,
        species = species,
        isFavorite = isFavorite
    )
}

fun List<CharacterEntity>.characterEntityToCharacters(): List<Character> {
    return map { it.toCharacter() }
}