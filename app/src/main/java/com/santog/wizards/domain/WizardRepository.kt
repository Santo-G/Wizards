package com.santog.wizards.domain

import com.santog.wizards.domain.states.LoadCharacterResult

/**
 * Repository abstraction
 */
interface WizardRepository {
    @Suppress("TooGenericExceptionCaught")
    suspend fun loadCharacters(): LoadCharacterResult

    @Suppress("TooGenericExceptionCaught")
    suspend fun loadCharacter(name: String): LoadCharacterResult
}
