package com.santog.wizards

import android.app.Application
import com.santog.wizards.di.appModule
import com.santog.wizards.di.networkingKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class HPApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@HPApplication)
            modules(appModule, networkingKoinModule)
        }
    }
}
