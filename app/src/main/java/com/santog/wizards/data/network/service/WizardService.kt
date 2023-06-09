package com.santog.wizards.data.network.service

import com.santog.wizards.data.network.dto.CharacterDTO
import retrofit2.http.GET

interface WizardService {
    @GET("characters")
    suspend fun loadCharacters(): CharacterDTO
}
