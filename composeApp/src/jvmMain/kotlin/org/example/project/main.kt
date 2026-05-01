package org.example.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.data.DatabaseDriverFactory

fun main() = application {
    val dbHelper = remember { DatabaseHelper(DatabaseDriverFactory()) }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Tugas7pam",
    ) {
        App(dbHelper.repository)
    }
}
