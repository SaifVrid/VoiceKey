package com.example.voicekey.data.local

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class UserVoiceEntity(
    @Id var id: Long = 0,
    val userName : String,
    val voicePath : String
)