package com.example.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @ColumnInfo(name = "api_id") var apiId: Int,
    var image: String,
    var name: String,
    var status: String,
    var species: String,
    var gender: String,
    var isFavorite: Boolean = true
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
