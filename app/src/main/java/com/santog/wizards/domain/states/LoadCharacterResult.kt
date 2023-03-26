package com.santog.wizards.domain.states

import com.santog.wizards.domain.model.Character

sealed class LoadCharacterResult {
    object Loading : LoadCharacterResult()
    data class Success(val characters: List<Character>) : LoadCharacterResult()
    data class Failure(val error: LoadCharacterError) : LoadCharacterResult()
}
