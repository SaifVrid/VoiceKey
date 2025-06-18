
package com.example.voicekey.ui.screens.voiceRecordScreen

import com.example.voicekey.data.local.UserVoiceEntity

class SaveRecordedVoiceUseCase(
    private val voiceRepo: VoiceRepo
) {
    suspend operator fun invoke(userName: String, filePath: String) {
        val entity = UserVoiceEntity(
            userName = userName,
            voicePath = filePath
        )
        voiceRepo.saveVoice(entity)
    }
}
