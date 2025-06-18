package com.example.voicekey.ui.components

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ModernRecordButton(
    modifier: Modifier = Modifier,
    isRecording: Boolean,
    onRecordToggle: (Boolean) -> Unit,
    buttonSize: Dp = 70.dp,
    idleColor: Color = Color(0xFF4CC9F0),
    recordingColor: Color = Color(0xFFE94560)
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) onRecordToggle(true)
    }

    val buttonScale by animateFloatAsState(
        targetValue = if (isRecording) 0.85f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
    )

    val buttonColor by animateColorAsState(
        targetValue = if (isRecording) recordingColor else idleColor,
        animationSpec = tween(300)
    )

    // Pulsation effect when recording
    val infiniteTransition = rememberInfiniteTransition()
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        if (isRecording) {
            Box(
                modifier = Modifier
                    .size(buttonSize * 1.8f)
                    .background(
                        color = recordingColor.copy(alpha = 0.2f),
                        shape = CircleShape
                    )
                    .scale(pulseScale)
            )
        }

        Box(
            modifier = Modifier
                .size(buttonSize)
                .scale(buttonScale)
                .clip(CircleShape)
                .background(buttonColor)
                .hapticClickable {
                    if (isRecording) {
                        onRecordToggle(false)
                    } else {
                        if (context.hasAudioPermission()) {
                            onRecordToggle(true)
                        } else {
                            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
                contentDescription = if (isRecording) "Stop" else "Record",
                tint = Color.White,
                modifier = Modifier.size(buttonSize * 0.5f)
            )
        }
    }
}



private fun Context.hasAudioPermission(): Boolean {
    return checkSelfPermission(Manifest.permission.RECORD_AUDIO) ==
            android.content.pm.PackageManager.PERMISSION_GRANTED
}

