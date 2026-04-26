package com.example.myprofileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.example.myprofileapp.db.DataStoreFactory
import com.example.myprofileapp.db.DatabaseDriverFactory
import com.example.myprofileapp.db.createDatabase

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = createDatabase(DatabaseDriverFactory(this))
        val dataStore = DataStoreFactory(this).create()

        setContent {
            App(database, dataStore)
        }
    }
}
