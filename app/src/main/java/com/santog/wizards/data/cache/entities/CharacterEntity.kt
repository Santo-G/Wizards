package com.santog.wizards.data.cache.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.santog.wizards.data.network.dto.Wand


@Entity
data class CharacterEntity(
    @PrimaryKey val id: String,
    val name: String,
    val actor: String,
    val alive: Boolean,
    val alternateActors: List<String>,
    val alternateNames: List<String>,
    val ancestry: String,
    val dateOfBirth: String,
    val eyeColour: String,
    val gender: String,
    val hairColour: String,
    val hogwartsStaff: Boolean,
    val hogwartsStudent: Boolean,
    val house: String,
    val image: String,
    val patronus: String,
    val species: String,
    val wand: Wand,
    val wizard: Boolean,
    val yearOfBirth: Int
)
