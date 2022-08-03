package com.mzzlab.demo.countriesapp

import android.app.Application
import androidx.core.provider.FontRequest
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CountriesApp: Application() {

    override fun onCreate() {
        super.onCreate();
        initLogger();
        initEmoji();
    }

    private fun initEmoji() {
        val config: EmojiCompat.Config = BundledEmojiCompatConfig(applicationContext)
        EmojiCompat.init(config)
    }

    private fun initLogger() {
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

}