package com.example.myprofileapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import com.example.myprofileapp.db.DataStoreFactory
import com.example.myprofileapp.db.DatabaseDriverFactory
import com.example.myprofileapp.db.createDatabase

fun main() = application {
    val database = createDatabase(DatabaseDriverFactory())
    val dataStore = DataStoreFactory().create()
    
    Window(
        onCloseRequest = ::exitApplication,
        title = "MyProfileApp",
    ) {
        App(database, dataStore)
    }
}
