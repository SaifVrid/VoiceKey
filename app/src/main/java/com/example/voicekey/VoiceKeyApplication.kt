package com.example.voicekey

import android.app.Application
import com.example.voicekey.di.appModule
import com.example.voicekey.utils.ObjectBoxBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class VoiceKeyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ObjectBoxBuilder.init(this)
        startKoin {
            androidContext(this@VoiceKeyApplication)
            modules(appModule)
        }
    }
}
