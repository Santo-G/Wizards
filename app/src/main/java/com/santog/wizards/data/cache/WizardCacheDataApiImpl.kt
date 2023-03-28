package com.santog.wizards.data.cache

import com.santog.wizards.data.cache.dao.AppDatabase
import com.santog.wizards.data.cache.entities.CharacterEntity
import com.santog.wizards.data.model.CharacterExternalDataModel

class WizardCacheDataApiImpl(
    val db : AppDatabase
) : WizardCacheDataAPI {
    private val characterDao = db.characterDao()
/*

    override suspend fun loadCharacters(): List<CharacterExternalDataModel> {
        try {
            val charactersList = characterDao.getAll()
            val characters = charactersList.mapNotNull {
                it.toExternalDataModel()
            }
            return characters.ifEmpty {
                emptyList()
            }
        } catch (e: IOException) {
            Timber.e(e, "IO Exception on LoadCharacters raised")
            return emptyList()
        } catch (e: Exception) {
            Timber.e(e, "Generic Exception on LoadCharacters raised")
            return emptyList()
        }
    }

    override suspend fun loadCharacter(name: String): CharacterExternalDataModel? {
        try {
            val characterEntity = characterDao.findByName(name)
            return if (characterEntity == null) {
                null
            } else {
                val characterExternalData = characterEntity.toExternalDataModel()
                if (characterExternalData == null) {
                    Timber.e(Throwable("Invalid character name"))
                    null
                } else {
                    characterExternalData
                }
            }
        } catch (e: IOException) {
            Timber.e(e, "IO Exception on loadCharacter in WizardCacheDataApiImpl raised")
            return null
        } catch (e: Exception) {
            Timber.e(e, "Generic Exception on loadCharacter in WizardCacheDataApiImpl raised")
            return null
        }
    }

    override suspend fun updateData(charactersList: List<CharacterExternalDataModel>): Boolean {
        val characters = charactersList.mapNotNull {
            it.toEntityModel()
        }
        return try {
            characterDao.insertAll(characters)
            Timber.d("Successfully Db updated")
            true
        } catch (e: Exception) {
            Timber.e(e, "Database Exception on updateData raised")
            false
        }
    }

    override suspend fun clearTable() {
        try {
            characterDao.clearTable()
            Timber.d("Successfully clear table executed")
        } catch (e: Exception) {
            Timber.e(e, "Database Exception on clearTable raised")
        }
    }

    private fun CharacterEntity.toExternalDataModel(): CharacterExternalDataModel? {
        val id = id
        return if (id != null) {
            CharacterExternalDataModel(
                id = id,
                name = name,
                actor = actor,
                alive = alive,
                ancestry = ancestry,
                dateOfBirth = dateOfBirth,
                eyeColour = eyeColour,
                gender = gender,
                hairColour = hairColour,
                hogwartsStaff = hogwartsStaff,
                hogwartsStudent = hogwartsStudent,
                house = house,
                image = image,
                patronus = patronus,
                species = species,
                wizard = wizard,
                yearOfBirth = yearOfBirth
            )
        } else {
            null
        }
    }


    private fun CharacterExternalDataModel.toEntityModel(): CharacterEntity? {
        val id = id
        return if (id != null) {
            CharacterEntity(
                id = id,
                name = name,
                actor = actor,
                alive = alive,
                ancestry = ancestry,
                dateOfBirth = dateOfBirth,
                eyeColour = eyeColour,
                gender = gender,
                hairColour = hairColour,
                hogwartsStaff = hogwartsStaff,
                hogwartsStudent = hogwartsStudent,
                house = house,
                image = image,
                patronus = patronus,
                species = species,
                wizard = wizard,
                yearOfBirth = yearOfBirth
            )
        } else {
            null
        }
    }
*/

}
