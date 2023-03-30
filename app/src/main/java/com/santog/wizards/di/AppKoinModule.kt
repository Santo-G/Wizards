package com.santog.wizards.di

import androidx.room.Room
import com.santog.wizards.data.WizardRepositoryImpl
import com.santog.wizards.data.cache.dao.AppDatabase
import com.santog.wizards.domain.WizardRepository
import com.santog.wizards.presentation.viewmodel.DetailViewModel
import com.santog.wizards.presentation.viewmodel.HomeViewModel
import com.santog.wizards.utils.WizardsSharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        WizardsSharedPreferences(get())
    }

    single<WizardRepository> {
        WizardRepositoryImpl(wizardNetwork = get(), wizardCache = get(), sharedPreferences = get())
    }

    viewModel { HomeViewModel(wizardRepository = get()) }
    viewModel { DetailViewModel(wizardRepository = get()) }

}
