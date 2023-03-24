package com.santog.wizards.data

import com.santog.wizards.data.model.CharacterExternalDataModel

interface WizardDataAPI {
    @Suppress("TooGenericExceptionCaught")
    suspend fun loadCharacters(): List<CharacterExternalDataModel>

    @Suppress("TooGenericExceptionCaught")
    suspend fun loadCharacter(name: String): CharacterExternalDataModel?
}
