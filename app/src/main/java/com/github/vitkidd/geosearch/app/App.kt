package com.github.vitkidd.geosearch.app

import android.app.Application
import com.github.vitkidd.geosearch.BuildConfig
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}