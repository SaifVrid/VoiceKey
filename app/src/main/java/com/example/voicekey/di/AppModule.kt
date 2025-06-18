package com.example.voicekey.di


import com.example.voicekey.data.local.UserVoiceEntity
import com.example.voicekey.ui.screens.voiceRecordScreen.SaveRecordedVoiceUseCase
import com.example.voicekey.ui.screens.voiceRecordScreen.VoiceRecordScreenModel
import com.example.voicekey.ui.screens.voiceRecordScreen.VoiceRepo
import com.example.voicekey.ui.screens.voiceRecordScreen.VoiceRepoImpl
import com.example.voicekey.utils.ObjectBoxBuilder
import io.objectbox.Box
import org.koin.dsl.module


val appModule = module {
    // Provide Box<UserVoiceEntity> from your ObjectBoxBuilder
    single<Box<UserVoiceEntity>> { ObjectBoxBuilder.boxStore.boxFor(UserVoiceEntity::class.java) }

    // Repo
    single<VoiceRepo> { VoiceRepoImpl(get()) }

    // UseCase
    factory { SaveRecordedVoiceUseCase(get()) }

    // ScreenModel
    factory { VoiceRecordScreenModel(get()) }
}