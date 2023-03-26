package com.santog.wizards.data

import com.santog.wizards.data.cache.WizardCacheDataAPI
import com.santog.wizards.data.model.CharacterExternalDataModel
import com.santog.wizards.data.network.WizardNetworkDataAPI
import com.santog.wizards.domain.WizardRepository
import com.santog.wizards.domain.model.Character
import com.santog.wizards.domain.model.Wand
import com.santog.wizards.domain.states.LoadCharacterError
import com.santog.wizards.domain.states.LoadCharacterResult
import com.santog.wizards.utils.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

/**
 * Repository implementation
 */
class WizardRepositoryImpl(
    private val wizardCache: WizardCacheDataAPI,
    private val wizardNetwork: WizardNetworkDataAPI,
    private val sharedPreferences: SharedPreferences
) : WizardRepository {

    private lateinit var state: LoadCharacterResult

    companion object {
        private const val CHARACTERS_CACHE_TIME: Long = 300000 // 5 minutes
    }

    override suspend fun loadCharacters(): LoadCharacterResult {
        try {
            state = LoadCharacterResult.Loading
            val now = Calendar.getInstance().timeInMillis
            val lastTimeChecked = sharedPreferences.getLongPreference(SharedPreferences.LAST_NETWORK_LOOKUP)

            if (now > lastTimeChecked + CHARACTERS_CACHE_TIME) { // if more than 5 minutes has passed since last time I fetched characters from web service
                getCharacters().collect() { flowResult ->
                    state = if (flowResult.isEmpty()) {
                        LoadCharacterResult.Failure(LoadCharacterError.NoInternet)
                    } else {
                        wizardCache.clearTable() // clear table in order to fetch new characters from web service
                        wizardCache.updateData(flowResult)  // updated local Db data
                        val charactersList = flowResult.mapNotNull {
                            it.toDomain()
                        }
                        LoadCharacterResult.Success(charactersList)
                    }
                }
            }
            sharedPreferences.setLongPreference(SharedPreferences.LAST_NETWORK_LOOKUP, now)
        } catch (e: Exception) {
            Timber.e(e, "Exception on LoadCharacters raised")
            state = LoadCharacterResult.Failure(LoadCharacterError.DbError)
        }
        return state
    }

    private fun getCharacters(): Flow<List<CharacterExternalDataModel>> {
        var response: List<CharacterExternalDataModel>
        return flow<List<CharacterExternalDataModel>> {
            CoroutineScope(Dispatchers.IO).launch {
                response = wizardNetwork.loadCharacters()
                if (response.isNullOrEmpty()) {
                    emit(emptyList<CharacterExternalDataModel>())
                } else {
                    val characters = response
                    emit(characters)
                }
            }
        }
    }

    override suspend fun loadCharacter(name: String): LoadCharacterResult {
        return LoadCharacterResult.Failure(LoadCharacterError.ServerError)
    }

    private fun CharacterExternalDataModel.toDomain(): Character? {
        val id = id
        return if (id != null) {
            Character(
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

    private fun mapWandField(characterExternalDataModel: CharacterExternalDataModel): Wand {
        return Wand(
            core = characterExternalDataModel.wand.core,
            length = characterExternalDataModel.wand.length,
            wood = characterExternalDataModel.wand.wood
        )
    }

}
