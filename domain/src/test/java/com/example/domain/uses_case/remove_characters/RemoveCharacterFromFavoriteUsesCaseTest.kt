package com.example.domain.uses_case.remove_characters

import app.cash.turbine.test
import com.example.domain.common.Resource
import com.example.domain.model.Character
import com.example.domain.repository.CharactersRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class RemoveCharacterFromFavoriteUsesCaseTest {
    private lateinit var removeCharacterFromFavoriteUsesCase: RemoveCharacterFromFavoriteUsesCase

    @RelaxedMockK
    private lateinit var charactersRepository: CharactersRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        removeCharacterFromFavoriteUsesCase =
            RemoveCharacterFromFavoriteUsesCase(charactersRepository)
    }

    @Test
    fun whenAffectedRowsAreZero() = runTest {
        coEvery { charactersRepository.deleteFromFavorites(any()) } answers { 0 }

        val character = mockk<Character>()

        removeCharacterFromFavoriteUsesCase(character).test {
            assert(awaitItem() is Resource.Loading)
            assert(awaitItem() is Resource.Error)
            awaitComplete()
            //cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenAffectedRowsAreHigherThanZero() = runTest {
        coEvery { charactersRepository.deleteFromFavorites(4) } answers { 1 }

        val character = mockk<Character>(relaxed = true)

        removeCharacterFromFavoriteUsesCase(character).test {
            assert(awaitItem() is Resource.Loading)
            assert(awaitItem() is Resource.Success)
            awaitComplete()
        }
    }
}