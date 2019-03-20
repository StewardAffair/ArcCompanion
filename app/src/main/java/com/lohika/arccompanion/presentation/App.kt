package com.lohika.arccompanion.presentation

import android.app.Application
import com.lohika.arccompanion.presentation.di.appModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule))
    }
}