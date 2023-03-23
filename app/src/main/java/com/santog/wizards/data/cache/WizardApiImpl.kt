package com.santog.wizards.data.cache

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.santog.wizards.data.network.CharacterDTO
import com.santog.wizards.domain.WizardAPI
import com.santog.wizards.domain.model.Character
import com.santog.wizards.domain.states.LoadCharacterError
import com.santog.wizards.domain.states.LoadCharacterResult
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

class WizardApiImpl : WizardAPI {

    private val db : AppDatabase = Room
        .databaseBuilder(getApplicationContext(), AppDatabase::class.java, "wizards")
        .build()
    private val characterDao = db.characterDao()

    override suspend fun loadCharacters(): LoadCharacterResult {
        try {
            val charactersList = characterDao.getAll()
            val characters = charactersList.mapNotNull {
                it.toDomain()
            }
            return if (characters.isEmpty()) {
                LoadCharacterResult.Failure(LoadCharacterError.NoCharacterFound)
            } else {
                LoadCharacterResult.Success(characters)
            }
        } catch (e: IOException) {
            Timber.e(e, "IO Exception on LoadCharacter raised")
            return LoadCharacterResult.Failure(LoadCharacterError.NoInternet)
        } catch (e: Exception) {
            Timber.e(e, "Generic Exception on LoadCharacter raised")
            return LoadCharacterResult.Failure(LoadCharacterError.ServerError)
        }
    }

    private fun CharacterEntity.toDomain(): Character? {
        val id = id
        return if (id != null) {
            Character(
                name = name,
                image = image,
                wizard = wizard,
                id = id
            )
        } else {
            null
        }
    }


}
