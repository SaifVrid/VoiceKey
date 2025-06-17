package com.example.voicekey

import android.app.Application
import com.example.voicekey.utils.ObjectBoxBuilder

class VoiceKeyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ObjectBoxBuilder.init(this)
    }
}