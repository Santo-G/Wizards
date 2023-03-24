package com.santog.wizards.data.cache

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.santog.wizards.data.WizardDataAPI
import com.santog.wizards.data.cache.dao.AppDatabase
import com.santog.wizards.data.cache.entities.CharacterEntity
import com.santog.wizards.data.model.CharacterExternalDataModel
import com.santog.wizards.data.model.Wand
import com.santog.wizards.data.states.LoadExternalCharacterError.*
import com.santog.wizards.data.states.LoadExternalCharacterResult
import com.santog.wizards.data.states.LoadExternalCharacterResult.Failure
import com.santog.wizards.data.states.LoadExternalCharacterResult.Success
import timber.log.Timber
import java.io.IOException

class WizardDataApiImpl : WizardDataAPI {

    private val db: AppDatabase = Room
        .databaseBuilder(getApplicationContext(), AppDatabase::class.java, "wizards")
        .build()
    private val characterDao = db.characterDao()

    override suspend fun loadCharacters(): LoadExternalCharacterResult {
        try {
            val charactersList = characterDao.getAll()
            val characters = charactersList.mapNotNull {
                it.toExternalDataModel()
            }
            return if (characters.isEmpty()) {
                Failure(NoExternalCharacterFound)
            } else {
                Success(characters)
            }
        } catch (e: IOException) {
            Timber.e(e, "IO Exception on LoadCharacter raised")
            return Failure(DbError)
        } catch (e: Exception) {
            Timber.e(e, "Generic Exception on LoadCharacter raised")
            return Failure(DbError)
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
