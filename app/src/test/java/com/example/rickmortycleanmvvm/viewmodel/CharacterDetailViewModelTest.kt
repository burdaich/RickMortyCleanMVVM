package com.example.rickmortycleanmvvm.viewmodel

import app.cash.turbine.test
import com.example.domain.common.Resource
import com.example.domain.model.Character
import com.example.domain.uses_case.add_characters.AddCharacterToFavoriteUsesCase
import com.example.domain.uses_case.get_characters.GetCharacterFavoriteByApiIdUsesCase
import com.example.domain.uses_case.remove_characters.RemoveCharacterFromFavoriteUsesCase
import com.example.rickmortycleanmvvm.model.ErrorType
import com.example.rickmortycleanmvvm.utils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class CharacterDetailViewModelTest {
    private lateinit var characterDetailViewModel: CharacterDetailViewModel

    @RelaxedMockK
    private lateinit var addCharacterToFavoriteUsesCase: AddCharacterToFavoriteUsesCase

    @RelaxedMockK
    private lateinit var getCharacterFavoriteByApiIdUsesCase: GetCharacterFavoriteByApiIdUsesCase

    @RelaxedMockK
    private lateinit var removeCharacterFromFavoriteUsesCase: RemoveCharacterFromFavoriteUsesCase

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        characterDetailViewModel = CharacterDetailViewModel(
            addCharacterToFavoriteUsesCase,
            getCharacterFavoriteByApiIdUsesCase,
            removeCharacterFromFavoriteUsesCase
        )
    }

    @Test
    fun whenIsFavoriteCallRemoveFromFavoritesUsesCase() = runTest {
        val character = Character(
            gender = "",
            id = 0,
            image = "",
            name = "",
            status = "",
            species = "",
            isFavorite = true
        )

        coEvery { removeCharacterFromFavoriteUsesCase(character) } answers {
            flowOf(
                Resource.Success(0)
            )
        }
        characterDetailViewModel.manageCharacter(character)
        delay(1)
        coVerify(exactly = 1) { removeCharacterFromFavoriteUsesCase(character) }

        launch {
            characterDetailViewModel.state.test {
                assert(awaitItem().error == ErrorType.IsQueryError)
                awaitComplete()
            }
        }
    }

    @Test
    fun whenNotFavoriteCallAddFavoritesUsesCase() = runTest {
        val character = Character(
            gender = "",
            id = 0,
            image = "",
            name = "",
            status = "",
            species = "",
            isFavorite = true
        )

        coEvery { removeCharacterFromFavoriteUsesCase(character) } answers {
            flowOf(
                Resource.Success(0)
            )
        }
        characterDetailViewModel.manageCharacter(character)
        delay(1)
        coVerify(exactly = 1) { removeCharacterFromFavoriteUsesCase(character) }
    }
}