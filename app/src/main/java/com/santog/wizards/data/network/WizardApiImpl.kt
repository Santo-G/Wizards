package com.santog.wizards.data.network

import com.santog.wizards.domain.model.Character
import com.santog.wizards.domain.states.LoadCharacterError
import com.santog.wizards.domain.states.LoadCharacterResult
import com.santog.wizards.domain.states.LoadCharacterResult.*
import com.santog.wizards.domain.WizardAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

class WizardApiImpl: WizardAPI {
    private val service: WizardService

    init {
        val client = OkHttpClient.Builder()
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/characters")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        service = retrofit.create(WizardService::class.java)
    }

    @Suppress("TooGenericExceptionCaught")
    override suspend fun loadCharacters(): LoadCharacterResult {
        try {
            val charactersList = service.loadCharacters()
            val characters = charactersList.characters.mapNotNull {
                it.toDomain()
            }
            return if (characters.isEmpty()) {
                Failure(LoadCharacterError.NoCharacterFound)
            } else {
                Success(characters)
            }
        } catch (e: IOException) {
            Timber.e(e, "IO Exception on LoadCharacter raised")
            return Failure(LoadCharacterError.NoInternet)
        } catch (e: SocketTimeoutException) {
            Timber.e(e, "Socket Timeout Exception on LoadCharacter raised")
            return Failure(LoadCharacterError.SlowInternet)
        } catch (e: Exception) {
            Timber.e(e, "Generic Exception on LoadCharacter raised")
            return Failure(LoadCharacterError.ServerError)
        }
    }

    private fun CharacterDTO.CharacterDTOItem.toDomain(): Character? {
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
