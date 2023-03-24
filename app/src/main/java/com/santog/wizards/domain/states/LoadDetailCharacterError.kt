package com.santog.wizards.domain.states

sealed class LoadDetailCharacterError {
    object NoDetailCharacterFound : LoadDetailCharacterError()
    object NoInternet : LoadDetailCharacterError()
    object SlowInternet : LoadDetailCharacterError()
    object ServerError : LoadDetailCharacterError()
    object DbError : LoadDetailCharacterError()
}
