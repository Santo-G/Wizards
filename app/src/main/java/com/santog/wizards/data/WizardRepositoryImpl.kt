package com.santog.wizards.data

import com.santog.wizards.data.cache.WizardCacheDataAPI
import com.santog.wizards.data.model.CharacterExternalDataModel
import com.santog.wizards.data.network.WizardNetworkDataAPI
import com.santog.wizards.domain.WizardRepository
import com.santog.wizards.domain.model.Character
import com.santog.wizards.domain.model.Wand
import com.santog.wizards.domain.states.LoadCharacterError
import com.santog.wizards.domain.states.LoadCharacterResult

/**
 * Repository implementation
 */
class WizardRepositoryImpl(
    private val wizardCache: WizardCacheDataAPI,
    private val wizardNetwork: WizardNetworkDataAPI
) : WizardRepository {

    override suspend fun loadCharacters(): LoadCharacterResult {
        // TODO mapping towards domain layer
        return LoadCharacterResult.Failure(LoadCharacterError.ServerError)
    }

    override suspend fun loadCharacter(name: String): LoadCharacterResult {
        TODO("Not yet implemented")
        return LoadCharacterResult.Failure(LoadCharacterError.ServerError)
    }

    /*
        override fun loadCharacters(): Flow<List<Topic>> =
            topicDao.getTopicEntities()
                .map { it.map(TopicEntity::asExternalModel) }

        override fun loadCharacter(id: String): Flow<Topic> =
            topicDao.getTopicEntity(id).map { it.asExternalModel() }
            */

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
