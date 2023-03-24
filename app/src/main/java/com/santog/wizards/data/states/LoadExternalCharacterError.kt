package com.santog.wizards.data.states

sealed class LoadExternalCharacterError {
    object NoExternalCharacterFound : LoadExternalCharacterError()
    object NoInternet : LoadExternalCharacterError()
    object SlowInternet : LoadExternalCharacterError()
    object ServerError : LoadExternalCharacterError()
    object DbError : LoadExternalCharacterError()
}
