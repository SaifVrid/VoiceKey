package com.example.voicekey.ui.screens.voiceList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

object ListScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
//            Button(onClick = { navigator.pop() }) {
//                Text("Back to Main")
//            }
        Box(modifier = Modifier.fillMaxSize()) {

        }

    }
}

