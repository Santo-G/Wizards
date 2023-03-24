package com.santog.wizards.data.cache

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.santog.wizards.data.WizardDataAPI
import com.santog.wizards.data.cache.dao.AppDatabase
import com.santog.wizards.data.cache.entities.CharacterEntity
import com.santog.wizards.data.model.CharacterExternalDataModel
import com.santog.wizards.data.model.Wand
import timber.log.Timber
import java.io.IOException

class WizardDataApiImpl : WizardDataAPI {
    private val db: AppDatabase = Room
        .databaseBuilder(getApplicationContext(), AppDatabase::class.java, "wizards")
        .build()
    private val characterDao = db.characterDao()

    override suspend fun loadCharacters(): List<CharacterExternalDataModel> {
        try {
            val charactersList = characterDao.getAll()
            val characters = charactersList.mapNotNull {
                it.toExternalDataModel()
            }
            return if (characters.isEmpty()) {
                emptyList()
            } else {
                characters
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
            Timber.e(e, "IO Exception on LoadCharacter raised")
            return null
        } catch (e: Exception) {
            Timber.e(e, "Generic Exception on LoadCharacter raised")
            return null
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
                alternateActors = alternateActors,
                alternateNames = alternateNames,
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
                wand = mapWandField(this),
                wizard = wizard,
                yearOfBirth = yearOfBirth
            )
        } else {
            null
        }
    }

    private fun mapWandField(characterEntity: CharacterEntity): Wand {
        return Wand(
            core = characterEntity.wand.core,
            length = characterEntity.wand.length,
            wood = characterEntity.wand.wood
        )
    }

}
