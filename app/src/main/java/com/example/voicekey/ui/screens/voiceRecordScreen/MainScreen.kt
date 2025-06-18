package com.example.voicekey.ui.screens.voiceRecordScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.voicekey.ui.screens.voiceList.ListScreen

object VoiceRecordScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Column (
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var isRecording by remember { mutableStateOf(false) }

//            ModernRecordButton(
//                isRecording = isRecording,
//                onRecordToggle = { recording ->
//                    if (recording) {
//                        // Start recording logic
//                    } else {
//                        // Stop recording logic
//                    }
//                },
//                buttonSize = 88.dp,
//                idleColor = Color(0xFF00C853), // Green
//                recordingColor = Color(0xFFD50000), // Red
//                waveColor = Color(0xFFFF6D00) // Orange
//            )
            ModernRecordButton(
                isRecording = isRecording,
                onRecordToggle = { recording ->
                    isRecording = recording
                    if (recording) {
                        // Start actual audio recording here
                    } else {
                        // Stop recording
                    }
                },
                buttonSize = 88.dp,
                idleColor = Color(0xFF00C853), // Green
                recordingColor = Color(0xFFD50000), // Red
                waveColor = Color(0xFFFF6D00) // Orange
            )

//            Button (onClick = { navigator.push(ListScreen) }) {
//                Text("Go to List Screen")
//            }
        }
    }
}
