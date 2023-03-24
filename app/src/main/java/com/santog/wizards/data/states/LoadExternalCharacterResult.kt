package com.santog.wizards.data.states

import com.santog.wizards.data.model.CharacterExternalDataModel

sealed class LoadExternalCharacterResult {
    data class Success(val characters: List<CharacterExternalDataModel>) : LoadExternalCharacterResult()
    data class Failure(val error: LoadExternalCharacterError) : LoadExternalCharacterResult()
}
