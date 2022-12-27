package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.api.Api
import com.example.data.database.databases.CharacterDatabase
import com.example.data.repository.CharactersRepositoryImpl
import com.example.domain.common.Constants
import com.example.domain.repository.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideApi(): Api {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideCharacterBBDD(@ApplicationContext appContext: Context): CharacterDatabase {
        return Room.databaseBuilder(
            appContext,
            CharacterDatabase::class.java,
            CharacterDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCharactersRepository(
        api: Api,
        characterDatabase: CharacterDatabase
    ): CharactersRepository {
        return CharactersRepositoryImpl(api, characterDatabase)
    }
}