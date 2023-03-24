package com.santog.wizards.data.network

import com.santog.wizards.data.model.CharacterExternalDataModel

interface WizardNetworkDataAPI {
    @Suppress("TooGenericExceptionCaught")
    suspend fun loadCharacters(): List<CharacterExternalDataModel>

    @Suppress("TooGenericExceptionCaught")
    suspend fun loadCharacter(name: String): CharacterExternalDataModel?
}
