package com.santog.wizards.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.santog.wizards.data.model.CharacterExternalDataModel
import com.santog.wizards.domain.WizardRepository
import com.santog.wizards.domain.model.CharacterHP
import com.santog.wizards.domain.states.LoadCharacterResult
import com.santog.wizards.domain.states.LoadSearchCharacterResult
import com.santog.wizards.presentation.viewmodel.*
import kotlinx.coroutines.*
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val requestDelay = 500L

    val api = FakeWizardNetworkDataAPI(requestDelay)
    val viewModel = HomeViewModel(api)

    @Test
    fun when_on_ready_is_called_should_emit_loading_state() {
        viewModel.send(HomeScreenEvents.OnReady)
        val expected = HomeScreenStates.Loading
        assertThat(viewModel.states.value, `is`(expected))
    }
}

class FakeWizardNetworkDataAPI(val requestDelay: Long) : WizardRepository {
    var mockExternalCharactersList: List<CharacterExternalDataModel> = listOf(
        CharacterExternalDataModel(
            id = "fakeData1",
            name = "fakeData1",
            actor = "fakeData1",
            alive = true,
            ancestry = "fakeData1",
            dateOfBirth = "fakeData1",
            eyeColour = "fakeData1",
            gender = "fakeData1",
            hairColour = "fakeData1",
            hogwartsStaff = true,
            hogwartsStudent = false,
            house = "fakeData1",
            image = "fakeData1",
            patronus = "fakeData1",
            species = "fakeData1",
            wizard = true,
            yearOfBirth = 1963
        ),
        CharacterExternalDataModel(
            id = "fakeData2",
            name = "fakeData2",
            actor = "fakeData2",
            alive = false,
            ancestry = "fakeData2",
            dateOfBirth = "fakeData2",
            eyeColour = "fakeData2",
            gender = "fakeData2",
            hairColour = "fakeData2",
            hogwartsStaff = false,
            hogwartsStudent = true,
            house = "fakeData2",
            image = "fakeData2",
            patronus = "fakeData2",
            species = "fakeData2",
            wizard = false,
            yearOfBirth = 1953
        )
    )

    var mockCharactersHPList: List<CharacterHP> = listOf(
        CharacterHP(
            id = "fakeData1",
            name = "fakeData1",
            actor = "fakeData1",
            alive = true,
            ancestry = "fakeData1",
            dateOfBirth = "fakeData1",
            eyeColour = "fakeData1",
            gender = "fakeData1",
            hairColour = "fakeData1",
            hogwartsStaff = true,
            hogwartsStudent = false,
            house = "fakeData1",
            image = "fakeData1",
            patronus = "fakeData1",
            species = "fakeData1",
            wizard = true,
            yearOfBirth = 1963
        ),
        CharacterHP(
            id = "fakeData2",
            name = "fakeData2",
            actor = "fakeData2",
            alive = false,
            ancestry = "fakeData2",
            dateOfBirth = "fakeData2",
            eyeColour = "fakeData2",
            gender = "fakeData2",
            hairColour = "fakeData2",
            hogwartsStaff = false,
            hogwartsStudent = true,
            house = "fakeData2",
            image = "fakeData2",
            patronus = "fakeData2",
            species = "fakeData2",
            wizard = false,
            yearOfBirth = 1953
        )
    )

    override suspend fun loadCharacters(): LoadCharacterResult {
        delay(requestDelay)
        return LoadCharacterResult.Success(mockExternalCharactersList.map { character -> character.toDomain() })
    }

    override suspend fun loadCharacter(name: String): LoadSearchCharacterResult {
        return LoadSearchCharacterResult.Success(mockExternalCharactersList[0].toDomain())
    }

    private fun CharacterExternalDataModel.toDomain(): CharacterHP {
        return CharacterHP(
            id = id,
            name = name,
            actor = actor,
            alive = alive,
            ancestry = ancestry,
            dateOfBirth = dateOfBirth,
            eyeColour = eyeColour,
            gender = gender,
            hairColour = hairColour,
            hogwartsStaff = hogwartsStaff,
            hogwartsStudent = hogwartsStudent,
            house = house,
            image = image,
            patronus = patronus,
            species = species,
            wizard = wizard,
            yearOfBirth = yearOfBirth
        )
    }
}

