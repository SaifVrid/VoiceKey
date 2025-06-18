package com.example.voicekey.ui.screens.voiceRecordScreen


import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class VoiceRecordScreenModel(
    private val saveRecordedVoiceUseCase: SaveRecordedVoiceUseCase
) : StateScreenModel<VoiceRecordUiState>(VoiceRecordUiState()) {

    private val _uiState = MutableStateFlow(VoiceRecordUiState())
    val uiState: StateFlow<VoiceRecordUiState> = _uiState

    fun toggleRecording() {
        val isRecording = !_uiState.value.isRecording
        _uiState.value = _uiState.value.copy(isRecording = isRecording)

        if (isRecording) {
            startCountdown()
        }
    }

    private fun startCountdown() {
        screenModelScope.launch {
            var remaining = 15
            while (remaining > 0 && _uiState.value.isRecording) {
                delay(1000)
                remaining--
                _uiState.value = _uiState.value.copy(countdown = remaining)
            }

            // Stop recording
            _uiState.value = _uiState.value.copy(isRecording = false)

            // 🔥 Simulated file path and static user name
            val fakePath = "/data/user/0/com.example.voicekey/files/sample_audio.wav"
            val fakeUser = "Saif"

            // Log and save
            println("Saving voice for $fakeUser at $fakePath")
            saveRecordedVoiceUseCase(fakeUser, fakePath)
        }
    }
}


data class VoiceRecordUiState(
    val isRecording: Boolean = false,
    val countdown: Int = 15
)




