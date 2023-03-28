package com.santog.wizards.di

import com.santog.wizards.data.WizardRepositoryImpl
import com.santog.wizards.domain.WizardRepository
import com.santog.wizards.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<WizardRepository> {
        WizardRepositoryImpl(wizardNetwork = get()/*, wizardCache = get()*/)
    }

    viewModel { HomeViewModel(wizardRepository = get()) }

}
