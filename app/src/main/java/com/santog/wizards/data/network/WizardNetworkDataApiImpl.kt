package com.santog.wizards.data.network

import com.santog.wizards.data.model.CharacterExternalDataModel
import com.santog.wizards.data.model.Wand
import com.santog.wizards.data.network.dto.CharacterDTOItem
import com.santog.wizards.data.network.service.WizardService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException


class WizardNetworkDataApiImpl : WizardNetworkDataAPI {
    private val service: WizardService

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        service = retrofit.create(WizardService::class.java)
    }

    @Suppress("TooGenericExceptionCaught")
    override suspend fun loadCharacters(): List<CharacterExternalDataModel> {
        try {
            val charactersList = service.loadCharacters()
            val characters = charactersList.mapNotNull {
                it.toExternalDataModel()
            }
            return if (characters.isEmpty()) {
                emptyList()
            } else {
                characters
            }
        } catch (e: IOException) {
            Timber.e(e, "IO Exception on loadCharacters in WizardNetworkDataApiImpl raised")
            return emptyList()
        } catch (e: SocketTimeoutException) {
            Timber.e(e, "Socket Timeout Exception on loadCharacters in WizardNetworkDataApiImpl raised")
            return emptyList()
        } catch (e: Exception) {
            Timber.e(e, "Generic Exception on loadCharacters in WizardNetworkDataApiImpl raised")
            return emptyList()
        }
    }

    override suspend fun loadCharacter(name: String): CharacterExternalDataModel? {
        Timber.d("loadCharacter not implemented in network call")
        return null
    }

    private fun CharacterDTOItem.toExternalDataModel(): CharacterExternalDataModel? {
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
                wizard = wizard ?: false,
                yearOfBirth = yearOfBirth
            )
        } else {
            null
        }
    }

    private fun mapWandField(characterDtoItem: CharacterDTOItem): Wand {
        return Wand(
            core = characterDtoItem.wand?.core,
            length = characterDtoItem.wand?.length,
            wood = characterDtoItem.wand?.wood
        )
    }

}
