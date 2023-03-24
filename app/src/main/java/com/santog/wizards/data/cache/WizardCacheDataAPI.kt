package com.santog.wizards.data.cache

import com.santog.wizards.data.model.CharacterExternalDataModel

interface WizardCacheDataAPI {
    @Suppress("TooGenericExceptionCaught")
    suspend fun loadCharacters(): List<CharacterExternalDataModel>

    @Suppress("TooGenericExceptionCaught")
    suspend fun loadCharacter(name: String): CharacterExternalDataModel?
}
