package com.example.voicekey.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback

fun Modifier.hapticClickable(
    feedbackType: HapticFeedbackType = HapticFeedbackType.LongPress,
    onClick: () -> Unit
): Modifier = composed {
    val haptic = LocalHapticFeedback.current
    this.then(
        Modifier.clickable {
            haptic.performHapticFeedback(feedbackType)
            onClick()
        }
    )
}
