package com.example.data.extensions

import com.example.data.model.CharacterModel
import com.example.data.model.CharactersInfoModel
import com.example.data.model.CharactersModel
import com.example.domain.model.Character
import com.example.domain.model.Characters
import com.example.domain.model.CharactersInfo

fun CharactersModel.toCharacters(): Characters {
    return Characters(info = info.toCharactersInfo(), characters = results.toCharacters())
}

fun CharactersInfoModel.toCharactersInfo(): CharactersInfo {
    return CharactersInfo(count, next, pages, prev)
}

fun CharacterModel.toCharacter(): Character {
    return Character(created, episode, gender, id, image, name, status, species, url)
}

fun List<CharacterModel>.toCharacters(): List<Character> {
    return map { it.toCharacter() }
}