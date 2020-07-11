package com.example.pills

import android.app.Application
import timber.log.Timber

class PillApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}