package com.example.data.database.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.CharacterDao
import com.example.data.database.entities.CharacterEntity

@Database(
    entities = [CharacterEntity::class], version = 1,
    autoMigrations = [],
    exportSchema = true
)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object {
        const val DATABASE_NAME: String = "character.db"
    }
}