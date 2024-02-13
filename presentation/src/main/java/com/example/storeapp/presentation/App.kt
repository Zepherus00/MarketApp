package com.example.storeapp.presentation

import android.app.Application
import com.example.storeapp.presentation.di.dataModule
import com.example.storeapp.presentation.di.domainModule
import com.example.storeapp.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(presentationModule, domainModule, dataModule))
        }
    }
}