package com.example.voicekey

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.example.voicekey.ui.screens.voiceList.ListScreen
import com.example.voicekey.ui.screens.voiceRecordScreen.VoiceRecordScreen
import com.example.voicekey.ui.theme.VoiceKeyTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VoiceKeyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Navigator(screen = VoiceRecordScreen)
                }
            }
        }
    }
}

