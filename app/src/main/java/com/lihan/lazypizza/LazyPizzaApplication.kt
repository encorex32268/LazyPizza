package com.lihan.lazypizza

import android.app.Application
import com.google.firebase.FirebaseApp
import com.lihan.lazypizza.core.di.coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class LazyPizzaApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        startKoin{
            androidContext(this@LazyPizzaApplication)
            androidLogger(level = Level.DEBUG)
            modules(
                coreModule
            )
        }
    }

}