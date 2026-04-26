package com.example.myprofileapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import com.example.myprofileapp.di.sharedModule
import com.example.myprofileapp.di.platformModule
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(sharedModule, platformModule)
    }
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "MyProfileApp",
        ) {
            App()
        }
    }
}
