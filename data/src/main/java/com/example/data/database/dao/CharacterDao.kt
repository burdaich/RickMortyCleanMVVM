package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.data.database.entities.CharacterEntity

@Dao
interface CharacterDao {
    @Insert
    suspend fun insert(characterEntity: CharacterEntity): Long

    @Query("select id,api_id,image, name, status, species, gender, isFavorite from CharacterEntity where api_id=:apiId")
    suspend fun getFavoriteCharacter(apiId: Int): CharacterEntity?

    @Query("delete from CharacterEntity where api_id=:apiId")
    suspend fun deleteFromFavorites(apiId: Int): Int
}