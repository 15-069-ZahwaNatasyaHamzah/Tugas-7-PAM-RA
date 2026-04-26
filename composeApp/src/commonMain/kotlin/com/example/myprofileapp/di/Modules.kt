package com.example.myprofileapp.di

import com.example.myprofileapp.NoteRepository
import com.example.myprofileapp.SettingsRepository
import com.example.myprofileapp.NotesViewModel
import com.example.myprofileapp.ProfileViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { NoteRepository(get()) }
    single { SettingsRepository(get()) }
    
    factory { NotesViewModel(get(), get()) }
    factory { ProfileViewModel(get(), get()) }
}
