package com.santog.wizards.data.cache

import com.santog.wizards.data.cache.dao.AppDatabase
import com.santog.wizards.data.cache.entities.CharacterEntity
import com.santog.wizards.data.model.CharacterExternalDataModel
import timber.log.Timber
import java.io.IOException

class WizardCacheDataApiImpl(
    private val db : AppDatabase
) : WizardCacheDataAPI {
    private val characterDao = db.characterDao()

    override suspend fun loadCharacters(): List<CharacterExternalDataModel> {
        try {
            val charactersList = characterDao.getAll()
            val characters = charactersList.mapNotNull {
                it.toExternalDataModel()
            }
            return characters.ifEmpty {
                Timber.d("Retrieved empty characters list from db!")
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
            characters.map { character ->
                characterDao.insert(character)
            }
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

    override suspend fun insert(character: CharacterExternalDataModel): Boolean {
        val character = character.toEntityModel()
        try {
            if (character != null) {
                characterDao.insert(character)
                Timber.d("Successfully Db entity inserted")
                return true
            } else {
                Timber.d("Db entity not inserted correctly. Something went wrong!")
            }
        } catch (e: Exception) {
            Timber.e(e, "Database Exception on updateData raised")
        }
        return false
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
                name = name ?: "emptyName",
                actor = actor  ?: "emptyActor",
                alive = alive ?: false,
                ancestry = ancestry ?: "emptyAncestry",
                dateOfBirth = dateOfBirth ?: "emptyDateBirth",
                eyeColour = eyeColour ?: "emptyEyeColour",
                gender = gender ?: "emptyGender",
                hairColour = hairColour ?: "emptyHairColour",
                hogwartsStaff = hogwartsStaff ?: if (hogwartsStudent == true) false else true,
                hogwartsStudent = hogwartsStudent ?: if (hogwartsStaff == true) false else true,
                house = house ?: "emptyHouse",
                image = image ?: "",
                patronus = patronus ?: "emptyPatronus",
                species = species ?: "emptySpecies",
                wizard = wizard ?: false,
                yearOfBirth = yearOfBirth  ?: 0
            )
        } else {
            null
        }
    }

}
