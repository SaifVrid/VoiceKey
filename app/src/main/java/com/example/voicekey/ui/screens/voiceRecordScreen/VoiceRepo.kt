package com.example.voicekey.ui.screens.voiceRecordScreen
import com.example.voicekey.data.local.UserVoiceEntity
import io.objectbox.Box


//class VoiceRepo(private val voiceBox: Box<UserVoiceEntity>) {
//    fun saveVoice(voice: UserVoiceEntity) {
//        voiceBox.put(voice)
//    }
//}
interface VoiceRepo {
    suspend fun saveVoice(voice: UserVoiceEntity)
}
class VoiceRepoImpl(
    private val voiceBox: Box<UserVoiceEntity>
) : VoiceRepo {

    override suspend fun saveVoice(voice: UserVoiceEntity) {
        println("🎙 Saved voice for: ${voice.userName}, path: ${voice.voicePath}")
        voiceBox.put(voice)
    }
}