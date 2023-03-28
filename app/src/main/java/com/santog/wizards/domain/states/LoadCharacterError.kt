package com.santog.wizards.domain.states

sealed class LoadCharacterError {
    object NoCharacterFound : LoadCharacterError()
    object NoInternet : LoadCharacterError()
    object SlowInternet : LoadCharacterError()
    object ServerError : LoadCharacterError()
    object DbError : LoadCharacterError()
}
