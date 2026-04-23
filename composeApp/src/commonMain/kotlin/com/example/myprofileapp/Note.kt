package com.example.myprofileapp

data class Note(
    val id: String,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
