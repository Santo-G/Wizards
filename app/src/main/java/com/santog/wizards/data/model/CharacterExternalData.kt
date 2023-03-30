package com.santog.wizards.data.model

/**
 * External data layer representation of an "HP" Character
 */
data class CharacterExternalDataModel(
    val id: String,
    val name: String?,
    val actor: String?,
    val alive: Boolean?,
    val ancestry: String?,
    val dateOfBirth: String?,
    val eyeColour: String?,
    val gender: String?,
    val hairColour: String?,
    val hogwartsStaff: Boolean?,
    val hogwartsStudent: Boolean?,
    val house: String?,
    val image: String?,
    val patronus: String?,
    val species: String?,
    val wizard: Boolean?,
    val yearOfBirth: Int?
)
