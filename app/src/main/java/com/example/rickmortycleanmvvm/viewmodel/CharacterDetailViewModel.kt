package com.example.rickmortycleanmvvm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import com.example.domain.model.Character

class CharacterDetailViewModel @Inject constructor() : ViewModel() {

    fun addCharacterToFavorite(character: Character) {
        Log.d("tag", character.toString())
    }
}