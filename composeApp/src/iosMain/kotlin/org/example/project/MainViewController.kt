package org.example.project

import androidx.compose.ui.window.ComposeUIViewController
import org.example.project.data.DatabaseDriverFactory

fun MainViewController() = ComposeUIViewController { 
    val dbHelper = DatabaseHelper(DatabaseDriverFactory())
    App(dbHelper.repository) 
}
