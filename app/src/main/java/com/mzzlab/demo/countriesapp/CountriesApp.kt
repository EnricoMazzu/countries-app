package com.mzzlab.demo.countriesapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CountriesApp: Application() {

    override fun onCreate() {
        super.onCreate();
        initLogger();
        Timber.i("CountriesApp init done")
    }

    private fun initLogger() {
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

}