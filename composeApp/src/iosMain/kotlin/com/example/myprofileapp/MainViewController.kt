package com.example.myprofileapp

import androidx.compose.ui.window.ComposeUIViewController

import com.example.myprofileapp.di.sharedModule
import com.example.myprofileapp.di.platformModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController { 
    startKoin {
        modules(sharedModule, platformModule)
    }
    App()
}
