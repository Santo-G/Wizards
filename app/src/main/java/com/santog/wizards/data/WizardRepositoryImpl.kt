package com.santog.wizards.data

import com.santog.wizards.data.cache.WizardCacheDataAPI
import com.santog.wizards.data.model.CharacterExternalDataModel
import com.santog.wizards.data.network.WizardNetworkDataAPI
import com.santog.wizards.domain.WizardRepository
import com.santog.wizards.domain.model.CharacterHP
import com.santog.wizards.domain.states.LoadCharacterError
import com.santog.wizards.domain.states.LoadCharacterResult
import com.santog.wizards.domain.states.LoadSearchCharacterResult
import com.santog.wizards.utils.WizardsSharedPreferences
import timber.log.Timber
import java.util.*

/**
 * Repository implementation
 */
class WizardRepositoryImpl(
    private val wizardCache: WizardCacheDataAPI,
    private val wizardNetwork: WizardNetworkDataAPI,
    private val sharedPreferences: WizardsSharedPreferences
) : WizardRepository {

    private lateinit var state: LoadCharacterResult
    private lateinit var searchState: LoadSearchCharacterResult

    companion object {
        private const val CHARACTERS_CACHE_TIME: Long = 1000 * 60 * 60 * 12     // 6 hours
    }

    override suspend fun loadCharacters(): LoadCharacterResult {
        Timber.d("LoadCharacters invocation in WizardRepositoryImpl.")
        var charactersListExternal: List<CharacterExternalDataModel>
        var charactersListDomain: List<CharacterHP> = emptyList()
        try {
            Timber.d("Computing period since last web service call...")
            val now = Calendar.getInstance().timeInMillis
            val lastTimeChecked = sharedPreferences.getLongPreference(WizardsSharedPreferences.LAST_NETWORK_LOOKUP)
            if (now > lastTimeChecked + CHARACTERS_CACHE_TIME) {    // if is more than 6 hours since last time I get characters from web service
                charactersListExternal = getCharacters()    // trying to pick data from web service
                if (charactersListExternal.isNotEmpty()) {
                    wizardCache.clearTable()    // I have to clear table in order to fetch new characters from ws
                    charactersListDomain = charactersListExternal.mapNotNull {
                        it.toDomain()
                    }
                    charactersListDomain.map { character ->
                        wizardCache.insert(character.toExternalDataModel())
                    }
                }
            }

            val charactersFromDb = wizardCache.loadCharacters()

            if (charactersFromDb.isNullOrEmpty()) {
                state = LoadCharacterResult.Failure(LoadCharacterError.NoCharacterFound)
            } else if (!charactersFromDb.isNullOrEmpty()) {
                state = LoadCharacterResult.Success(charactersFromDb.mapNotNull { character ->
                    character.toDomain()
                })
            } else {
                state = LoadCharacterResult.Failure(LoadCharacterError.DbError)
            }
            sharedPreferences.setLongPreference(WizardsSharedPreferences.LAST_NETWORK_LOOKUP, now)

        } catch (e: Exception) {
            Timber.e(e, "Exception on LoadCharacters raised")
            state = LoadCharacterResult.Failure(LoadCharacterError.DbError)
        }
        return state
    }

    private suspend fun getCharacters(): List<CharacterExternalDataModel> {
        var response: List<CharacterExternalDataModel> = emptyList()
        response = wizardNetwork.loadCharacters()
        return response
    }


    override suspend fun loadCharacter(name: String): LoadSearchCharacterResult {
        Timber.d("LoadCharacter invocation in WizardRepositoryImpl.")
        val characterFromDb = wizardCache.loadCharacter(name)
        if (characterFromDb == null) {
            searchState = LoadSearchCharacterResult.Failure(LoadCharacterError.NoCharacterFound)
        } else if (characterFromDb != null) {
            searchState = LoadSearchCharacterResult.Success(characterFromDb.toDomain())
        } else {
            searchState = LoadSearchCharacterResult.Failure(LoadCharacterError.DbError)
        }
        return searchState
    }

    private fun CharacterExternalDataModel.toDomain(): CharacterHP {
        return CharacterHP(
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
    }

    private fun CharacterHP.toExternalDataModel(): CharacterExternalDataModel {
        return CharacterExternalDataModel(
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
    }

}
