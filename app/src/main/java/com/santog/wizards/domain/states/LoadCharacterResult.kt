package com.santog.wizards.domain.states

import com.santog.wizards.domain.model.CharacterHP

sealed class LoadCharacterResult {
    object Loading : LoadCharacterResult()
    data class Success(val characters: List<CharacterHP>) : LoadCharacterResult()
    data class Failure(val error: LoadCharacterError) : LoadCharacterResult()
}
