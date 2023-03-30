package com.santog.wizards.data.cache

import com.santog.wizards.data.model.CharacterExternalDataModel

interface WizardCacheDataAPI {
    @Suppress("TooGenericExceptionCaught")
    suspend fun loadCharacters(): List<CharacterExternalDataModel>

    @Suppress("TooGenericExceptionCaught")
    suspend fun loadCharacter(name: String): CharacterExternalDataModel?

    @Suppress("TooGenericExceptionCaught")
    suspend fun updateData(characters: List<CharacterExternalDataModel>): Boolean

    @Suppress("TooGenericExceptionCaught")
    suspend fun clearTable()

    @Suppress("TooGenericExceptionCaught")
    suspend fun insert(character: CharacterExternalDataModel): Boolean
}
