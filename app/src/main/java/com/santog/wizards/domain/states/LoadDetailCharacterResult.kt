package com.santog.wizards.domain.states

import com.santog.wizards.data.model.CharacterExternalDataModel

sealed class LoadDetailCharacterResult {
    data class Success(val character: CharacterExternalDataModel) : LoadDetailCharacterResult()
    data class Failure(val error: LoadDetailCharacterError) : LoadDetailCharacterResult()
}
