package com.example.voicekey.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.sin

@Composable
fun WavyBottomBox(
    modifier: Modifier = Modifier,
    isRecording: Boolean,
    backgroundColor: Color = Color.Blue,
    waveColor: Color = Color.Cyan,
    waveHeight: Dp = 30.dp,
    waveLength: Dp = 300.dp
) {
    val phase by animateFloatAsState(
        targetValue = if (isRecording) 2 * Math.PI.toFloat() else 0f,
        animationSpec = if (isRecording) infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ) else tween(500),
        label = "WavePhase"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val waveHeightPx = waveHeight.toPx()
            val waveLengthPx = waveLength.toPx()

            val path = Path().apply {
                moveTo(0f, size.height)
                for (x in 0..size.width.toInt()) {
                    val angle = (x / waveLengthPx) * 2 * Math.PI + phase
                    val y = (waveHeightPx * sin(angle)).toFloat() + size.height / 2
                    lineTo(x.toFloat(), y)
                }
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            drawRect(
                color = backgroundColor,
                size = size
            )

            drawPath(
                path = path,
                color = waveColor
            )
        }
    }
}

