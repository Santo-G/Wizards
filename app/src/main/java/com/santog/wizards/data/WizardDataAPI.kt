package com.santog.wizards.data

import com.santog.wizards.data.states.LoadExternalCharacterResult

interface WizardDataAPI {
    @Suppress("TooGenericExceptionCaught")
    suspend fun loadCharacters(): LoadExternalCharacterResult
}
