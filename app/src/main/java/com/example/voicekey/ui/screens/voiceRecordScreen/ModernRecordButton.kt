package com.example.voicekey.ui.screens.voiceRecordScreen

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voicekey.utils.hapticClickable

@Composable
fun ModernRecordButton(
    modifier: Modifier = Modifier,
    isRecording: Boolean,
    onRecordToggle: (Boolean) -> Unit,
    buttonSize: Dp = 80.dp,
    idleColor: Color = MaterialTheme.colorScheme.primary,
    recordingColor: Color = MaterialTheme.colorScheme.error,
    waveColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                onRecordToggle(true)
            }
        }
    )




    // Animation for button state
    val buttonScale by animateFloatAsState(
        targetValue = if (isRecording) 0.9f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
    )

    val buttonColor by animateColorAsState(
        targetValue = if (isRecording) recordingColor else idleColor,
        animationSpec = tween(300)
    )

    var showPermissionWarning by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Wave visualization container
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(buttonSize * 1.7f)

        ) {
            // Record button
            Box(
                modifier = Modifier
                    .size(buttonSize)
                    .scale(buttonScale)
                    .clip(CircleShape)
                    .background(buttonColor, CircleShape)
                    .hapticClickable {
                        if (isRecording) {
                            onRecordToggle(false)
                            showPermissionWarning = false
                        } else {
                            if (context.hasAudioPermission()) {
                                onRecordToggle(true)
                            } else {
                                showPermissionWarning = true
                                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
                    contentDescription = if (isRecording) "Stop Recording" else "Start Recording",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(buttonSize * 0.5f)
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // Status text
        when {
            isRecording -> RecordingStatus("Recording...", recordingColor)
            showPermissionWarning -> PermissionPrompt("Allow microphone access")
            else -> RecordingStatus("Tap to record", idleColor)
        }
    }
}

@Composable
private fun RecordingStatus(text: String, color: Color) {
    Text(
        text = text,
        color = color,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    )
}

@Composable
private fun PermissionPrompt(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.error,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    )
}



private fun Context.hasAudioPermission(): Boolean {
    return checkSelfPermission(Manifest.permission.RECORD_AUDIO) ==
            android.content.pm.PackageManager.PERMISSION_GRANTED
}

