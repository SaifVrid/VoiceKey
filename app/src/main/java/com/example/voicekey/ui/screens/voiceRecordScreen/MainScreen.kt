package com.example.voicekey.ui.screens.voiceRecordScreen


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.voicekey.ui.components.ModernRecordButton
import org.koin.compose.koinInject
import kotlin.math.sin


object VoiceRecordScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val saveUseCase: SaveRecordedVoiceUseCase = koinInject() // ✅ Composable scope

        val screenModel = rememberScreenModel {
            VoiceRecordScreenModel(saveUseCase) // ✅ Pure Kotlin lambda
        }



        val uiState by screenModel.uiState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Voice Key",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0F3460),
                ),
                modifier = Modifier.statusBarsPadding()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                VoiceRecordCard(
                    isRecording = uiState.isRecording,
                    countdown = uiState.countdown,
                    onRecordToggle = { screenModel.toggleRecording() }
                )
            }
        }
    }

}

// ... rest of the code remains the same ...
@Composable
fun VoiceRecordCard(
    isRecording: Boolean,
    countdown: Int,
    onRecordToggle: () -> Unit
) {
    val containerColor by animateColorAsState(
        targetValue = if (isRecording) Color(0xFF1A1A2E) else Color(0xFF16213E),
        animationSpec = tween(500)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Animated wave background
            if (isRecording) {
                WaveBackground(
                    modifier = Modifier.fillMaxSize(),
                    baseColor = Color(0xFF0F3460),
                    waveColor = Color(0xFFE94560).copy(alpha = 0.4f)
                )
            }

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Status indicator with animation
                RecordingStatus(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    isRecording = isRecording,
                    countdownSeconds = countdown,
                    onRecordToggle = onRecordToggle
                )


                // Modern record button with pulsation
                ModernRecordButton(
                    modifier = Modifier.padding(5.dp)
                        .fillMaxHeight(),
                    isRecording = isRecording,
                    onRecordToggle = {onRecordToggle()},
                    buttonSize = 70.dp
                )
            }
        }
    }
}

@Composable
fun RecordingStatus(
    modifier: Modifier = Modifier,
    isRecording: Boolean,
    countdownSeconds: Int,
    onRecordToggle: () -> Unit
) {
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isRecording) 1f else 0.6f,
        animationSpec = tween(300)
    )

    val formattedTime = String.format("%02d:%02d", countdownSeconds / 60, countdownSeconds % 60)

    Column(modifier = modifier.alpha(animatedAlpha)) {
        Text(
            text = if (isRecording) "Recording..." else "Ready to record",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = if (isRecording) formattedTime else "Press mic to start",
            color = if (isRecording) Color(0xFFE94560) else Color.White.copy(alpha = 0.7f),
            fontSize = 14.sp
        )
    }
}



@Composable
fun WaveBackground(
    modifier: Modifier = Modifier,
    baseColor: Color,
    waveColor: Color
) {
    val infiniteTransition = rememberInfiniteTransition()
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier) {
        // Draw base
        drawRect(color = baseColor, size = size)

        // Draw waves
        val path = Path().apply {
            val waveHeight = size.height / 8
            moveTo(0f, size.height)

            for (x in 0..size.width.toInt() step 10) {
                val y = (sin(x / 100f + phase) * waveHeight + size.height * 0.7f)
                        lineTo(x.toFloat(), y)
            }
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }

        drawPath(path, waveColor, alpha = 0.8f)
    }
}