package com.santog.wizards.data.network

import retrofit2.http.GET

interface WizardService {
    @GET("characters")
    suspend fun loadCharacters(): CharacterDTO
}
