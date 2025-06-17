package com.example.voicekey.utils

import android.content.Context
import com.example.voicekey.data.local.MyObjectBox
import io.objectbox.BoxStore

object ObjectBoxBuilder {
    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context)
            .build()
    }
}

