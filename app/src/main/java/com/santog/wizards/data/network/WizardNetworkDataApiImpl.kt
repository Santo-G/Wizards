package com.santog.wizards.data.network

import com.santog.wizards.data.model.CharacterExternalDataModel
import com.santog.wizards.data.model.Wand
import com.santog.wizards.data.network.dto.CharacterDTO
import com.santog.wizards.data.network.service.WizardService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

class WizardNetworkDataApiImpl : WizardNetworkDataAPI {
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
    override suspend fun loadCharacters(): List<CharacterExternalDataModel> {
        try {
            val charactersList = service.loadCharacters()
            val characters = charactersList.characters.mapNotNull {
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
        } catch (e: SocketTimeoutException) {
            Timber.e(e, "Socket Timeout Exception on LoadCharacters raised")
            return emptyList()
        } catch (e: Exception) {
            Timber.e(e, "Generic Exception on LoadCharacters raised")
            return emptyList()
        }
    }

    override suspend fun loadCharacter(name: String): CharacterExternalDataModel? {
        // TODO NOT NEEDED FOR NETWORK CALL - ONLY FOR DB CACHING ACTIVITIES !!!
        return null
    }

    private fun CharacterDTO.CharacterDTOItem.toExternalDataModel(): CharacterExternalDataModel? {
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

    private fun mapWandField(characterDtoItem: CharacterDTO.CharacterDTOItem): Wand {
        return Wand(
            core = characterDtoItem.wand.core,
            length = characterDtoItem.wand.length,
            wood = characterDtoItem.wand.wood
        )
    }

}
