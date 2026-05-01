package org.example.project

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport

import androidx.compose.material3.Text

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        Text("JS Target is currently configured for build only.")
    }
}
