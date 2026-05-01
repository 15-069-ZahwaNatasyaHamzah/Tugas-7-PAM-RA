package org.example.project.domain

import kotlinx.datetime.Clock

data class Note(
    val id: Long? = null,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false,
    val createdAt: Long = Clock.System.now().toEpochMilliseconds()
)
