package com.miftah.tedednew.app

import android.app.Application
import com.miftah.core.di.databaseModule
import com.miftah.core.di.networkModule
import com.miftah.core.di.preferenceModule
import com.miftah.core.di.repositoryModule
import com.miftah.tedednew.BuildConfig
import com.miftah.tedednew.app.di.useCaseModule
import com.miftah.tedednew.app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    preferenceModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}