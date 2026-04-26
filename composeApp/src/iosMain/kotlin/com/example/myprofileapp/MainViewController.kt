package com.example.myprofileapp

import androidx.compose.ui.window.ComposeUIViewController

import com.example.myprofileapp.db.DataStoreFactory
import com.example.myprofileapp.db.DatabaseDriverFactory
import com.example.myprofileapp.db.createDatabase

fun MainViewController() = ComposeUIViewController { 
    val database = createDatabase(DatabaseDriverFactory())
    val dataStore = DataStoreFactory().create()
    App(database, dataStore) 
}
