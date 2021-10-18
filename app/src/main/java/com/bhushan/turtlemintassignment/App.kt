package com.bhushan.turtlemintassignment

import android.app.Application
import com.bhushan.turtlemintassignment.koin.*
import com.bhushan.turtlemintassignment.utils.NetworkManager
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(repositoryModule, appExecutorsModule, viewModelModule, retrofitModule, apiModule, networkManagerModule, connectivityHelperModule))
        }
        val networkManager : NetworkManager by inject()
        networkManager.register(this)
    }
}