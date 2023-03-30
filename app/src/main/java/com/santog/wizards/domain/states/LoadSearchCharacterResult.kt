package com.santog.wizards.domain.states

import com.santog.wizards.domain.model.CharacterHP

sealed class LoadSearchCharacterResult {
    object Loading : LoadSearchCharacterResult()
    data class Success(val character: CharacterHP) : LoadSearchCharacterResult()
    data class Failure(val error: LoadCharacterError) : LoadSearchCharacterResult()
}
