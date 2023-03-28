package com.santog.wizards.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santog.wizards.domain.WizardRepository
import com.santog.wizards.domain.states.LoadCharacterError
import com.santog.wizards.domain.states.LoadCharacterResult
import com.santog.wizards.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailViewModel(
    private val wizardRepository: WizardRepository
) : ViewModel() {
    val states = MutableLiveData<DetailScreenStates>()
    val actions = SingleLiveEvent<DetailScreenActions>()

    fun send(event: DetailScreenEvents) {
        Timber.d(event.toString())
        when (event) {
            is DetailScreenEvents.OnReady -> {
                loadContent(event.characterId)
            }
        }
    }

    private fun loadContent(characterId: String) {
        states.postValue(DetailScreenStates.Loading)
        viewModelScope.launch {
            val result = wizardRepository.loadCharacters()
            when (result) {
                is LoadCharacterResult.Success -> {
                    val character = result.characters
                        .filter { it.id == characterId }
                        .map {
                            DetailCharacterUI(
                                id = it.id,
                                name = it.name,
                                actor = it.actor,
                                alive = it.alive,
                                ancestry = it.ancestry,
                                dateOfBirth = it.dateOfBirth,
                                eyeColour = it.eyeColour,
                                gender = it.gender,
                                hairColour = it.hairColour,
                                hogwartsStaff = it.hogwartsStaff,
                                hogwartsStudent = it.hogwartsStudent,
                                house = it.house,
                                image = it.image,
                                patronus = it.patronus,
                                species = it.species,
                                wizard = it.wizard,
                                yearOfBirth = it.yearOfBirth
                            )
                        }

                    val detailContent = DetailContent(character.first())
                    states.postValue(DetailScreenStates.Content(detailContent))
                }

                is LoadCharacterResult.Failure -> onFailure(result)
                else -> { /* DO NOOP */
                }
            }
        }
    }

    private fun onFailure(result: LoadCharacterResult.Failure) {
        when (result.error) {
            LoadCharacterError.NoCharacterFound -> states.postValue(DetailScreenStates.Error(DetailErrorStates.ShowNoCharacterFound))
            LoadCharacterError.NoInternet -> states.postValue(DetailScreenStates.Error(DetailErrorStates.ShowNoInternetMessage))
            LoadCharacterError.ServerError -> states.postValue(DetailScreenStates.Error(DetailErrorStates.ShowServerDetailError))
            LoadCharacterError.DbError -> states.postValue(DetailScreenStates.Error(DetailErrorStates.ShowDbDetailError))
            LoadCharacterError.SlowInternet -> states.postValue(DetailScreenStates.Error(DetailErrorStates.ShowSlowInternet))
        }
    }
}

sealed class DetailScreenStates {
    object Loading : DetailScreenStates()
    data class Error(val error: DetailErrorStates) : DetailScreenStates()
    data class Content(val detailContent: DetailContent) : DetailScreenStates()
}

sealed class DetailScreenEvents {
    data class OnReady(val characterId: String) : DetailScreenEvents()
}

sealed class DetailScreenActions {
    data class NavigateToDetail(val characterId: String /* TODO */) : DetailScreenActions()
}

sealed class DetailErrorStates {
    object ShowNoInternetMessage : DetailErrorStates()
    object ShowNoCharacterFound : DetailErrorStates()
    object ShowServerDetailError : DetailErrorStates()
    object ShowDbDetailError : DetailErrorStates()
    object ShowSlowInternet : DetailErrorStates()
}

class DetailContent(val character: DetailCharacterUI)

data class DetailCharacterUI(
    val id: String,
    val name: String?,
    val actor: String?,
    val alive: Boolean?,
    val ancestry: String?,
    val dateOfBirth: String?,
    val eyeColour: String?,
    val gender: String?,
    val hairColour: String?,
    val hogwartsStaff: Boolean?,
    val hogwartsStudent: Boolean?,
    val house: String?,
    val image: String?,
    val patronus: String?,
    val species: String?,
    val wizard: Boolean?,
    val yearOfBirth: Int?
)




