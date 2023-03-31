package com.santog.wizards.utils

import androidx.test.espresso.idling.CountingIdlingResource


object EspressoCountingIdlingResource {

    private const val RESOURCE = "GLOBAL"
    @JvmField val espressoTestIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        espressoTestIdlingResource.increment()
    }

    fun decrement() {
        if (!espressoTestIdlingResource.isIdleNow) {
            espressoTestIdlingResource.decrement()
        }
    }
}
