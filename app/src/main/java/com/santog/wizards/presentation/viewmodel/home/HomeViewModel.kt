package com.santog.wizards.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santog.wizards.domain.WizardRepository
import com.santog.wizards.domain.states.LoadCharacterError
import com.santog.wizards.domain.states.LoadCharacterResult
import com.santog.wizards.utils.EspressoCountingIdlingResource
import com.santog.wizards.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val wizardRepository: WizardRepository
) : ViewModel() {
    val states = MutableLiveData<HomeScreenStates>()
    val actions = SingleLiveEvent<HomeScreenActions>()

    fun send(event: HomeScreenEvents) {
        Timber.d(event.toString())
        when (event) {
            HomeScreenEvents.OnReady -> {
                loadContent()
            }
            is HomeScreenEvents.OnCharacterClick -> {
                actions.postValue(HomeScreenActions.NavigateToDetail(event.characterId))
            }
        }
    }

    private fun loadContent() {
        states.postValue(HomeScreenStates.Loading)
        viewModelScope.launch {
            EspressoCountingIdlingResource.increment()
            val result = wizardRepository.loadCharacters()
            when (result) {
                is LoadCharacterResult.Success -> {
                    val studentsList = result.characters
                        .filterNot { it.hogwartsStaff!! }
                        .map {
                            StudentsUI(
                                id = it.id,
                                characterName = it.name,
                                image = it.image,
                                staff = it.hogwartsStaff,
                                wizard = it.wizard
                            )
                        }
                    val staffList = result.characters
                        .filter { it.hogwartsStaff!! }
                        .map {
                            StaffUI(
                                id = it.id,
                                characterName = it.name,
                                image = it.image,
                                staff = it.hogwartsStaff,
                                wizard = it.wizard
                            )
                        }
                    val generalContent = GeneralContent(studentsList, staffList)
                    states.postValue(HomeScreenStates.Content(generalContent))
                    EspressoCountingIdlingResource.decrement()
                }

                is LoadCharacterResult.Failure -> onFailure(result)
                else -> { /* DO NOOP */ }
            }
        }
    }

    private fun onFailure(result: LoadCharacterResult.Failure) {
            when (result.error) {
                LoadCharacterError.NoCharacterFound -> states.postValue(HomeScreenStates.Error(ErrorStates.ShowNoCharacterFound))
                LoadCharacterError.NoInternet -> states.postValue(HomeScreenStates.Error(ErrorStates.ShowNoInternetMessage))
                LoadCharacterError.ServerError -> states.postValue(HomeScreenStates.Error(ErrorStates.ShowServerError))
                LoadCharacterError.DbError -> states.postValue(HomeScreenStates.Error(ErrorStates.ShowDbError))
                LoadCharacterError.SlowInternet -> states.postValue(HomeScreenStates.Error(ErrorStates.ShowSlowInternet))
            }
    }
}

sealed class HomeScreenStates {
    object Loading : HomeScreenStates()
    data class Error(val error: ErrorStates) : HomeScreenStates()
    data class Content(val generalContent: GeneralContent) : HomeScreenStates()
}

sealed class HomeScreenEvents {
    data class OnCharacterClick(val characterId: String) : HomeScreenEvents()
    object OnReady : HomeScreenEvents()
}

sealed class HomeScreenActions {
    data class NavigateToDetail(val characterId: String) : HomeScreenActions()
}

sealed class ErrorStates {
    object ShowNoInternetMessage : ErrorStates()
    object ShowNoCharacterFound : ErrorStates()
    object ShowServerError : ErrorStates()
    object ShowDbError : ErrorStates()
    object ShowSlowInternet : ErrorStates()
}

class GeneralContent(val studentsList: List<StudentsUI>, val staffList: List<StaffUI>)

data class StudentsUI(
    val id: String,
    val characterName: String?,
    val image: String?,
    val staff: Boolean?,
    val wizard: Boolean?
)

data class StaffUI(
    val id: String,
    val characterName: String?,
    val image: String?,
    val staff: Boolean?,
    val wizard: Boolean?
)
