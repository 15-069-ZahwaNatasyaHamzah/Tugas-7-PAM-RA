package com.example.myprofileapp

import com.example.myprofileapp.db.AppDatabase
import com.example.myprofileapp.db.NoteEntity
import kotlinx.coroutines.flow.Flow
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map

class NoteRepository(database: AppDatabase) {
    private val queries = database.appDatabaseQueries

    fun getAllNotes(): Flow<List<Note>> {
        return queries.getAllNotes().asFlow().mapToList(Dispatchers.Default).map { list ->
            list.map { it.toNote() }
        }
    }

    fun searchNotes(query: String): Flow<List<Note>> {
        return queries.searchNotes(query, query).asFlow().mapToList(Dispatchers.Default).map { list ->
            list.map { it.toNote() }
        }
    }

    fun insertNote(note: Note) {
        queries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            isFavorite = if (note.isFavorite) 1L else 0L,
            timestamp = note.timestamp
        )
    }

    fun deleteNote(id: String) {
        queries.deleteNote(id)
    }

    fun toggleFavorite(id: String) {
        queries.toggleFavorite(id)
    }

    fun getNoteById(id: String): Note? {
        return queries.getNoteById(id).executeAsOneOrNull()?.toNote()
    }

    private fun NoteEntity.toNote(): Note = Note(
        id = id,
        title = title,
        content = content,
        isFavorite = isFavorite == 1L,
        timestamp = timestamp
    )
}
