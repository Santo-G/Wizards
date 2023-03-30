package com.santog.wizards.di


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.santog.wizards.data.cache.WizardCacheDataAPI
import com.santog.wizards.data.cache.WizardCacheDataApiImpl
import com.santog.wizards.data.cache.dao.AppDatabase
import com.santog.wizards.data.cache.dao.CharacterDAO
import com.santog.wizards.data.network.WizardNetworkDataAPI
import com.santog.wizards.data.network.WizardNetworkDataApiImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkingKoinModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "wizards"
        ).build()
    }

    single<CharacterDAO> {
        val database = get<AppDatabase>()
        database.characterDao()
    }

    single<WizardNetworkDataAPI> {
        WizardNetworkDataApiImpl()
    }

    single<WizardCacheDataAPI> {
        WizardCacheDataApiImpl(get())
    }

/*    single{
        get<AppDatabase>().characterDao()
    }*/

}
