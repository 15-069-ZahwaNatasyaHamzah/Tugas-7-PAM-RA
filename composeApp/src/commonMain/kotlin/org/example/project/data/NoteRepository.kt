package org.example.project.data

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.Note
import org.example.project.database.NoteDatabase
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map

class NoteRepository(database: NoteDatabase) {
    private val queries = database.noteDatabaseQueries

    // Use a dispatcher that is safe for all platforms. 
    // Dispatchers.IO is not available on all targets (like JS/Wasm) in older coroutines versions.
    // However, since we are only using this in nonWebMain targets via nonWebMain configuration, it's safer.
    private val dispatcher = Dispatchers.Default 

    fun getAllNotes(): Flow<List<Note>> {
        return queries.getAllNotes()
            .asFlow()
            .mapToList(dispatcher)
            .map { list -> list.map { it.toNote() } }
    }

    fun getFavoriteNotes(): Flow<List<Note>> {
        return queries.getFavoriteNotes()
            .asFlow()
            .mapToList(dispatcher)
            .map { list -> list.map { it.toNote() } }
    }

    fun searchNotes(query: String): Flow<List<Note>> {
        return queries.searchNotes(query)
            .asFlow()
            .mapToList(dispatcher)
            .map { list -> list.map { it.toNote() } }
    }

    suspend fun getNoteById(id: Long): Note? {
        return queries.getNoteById(id).executeAsOneOrNull()?.toNote()
    }

    suspend fun insertNote(note: Note) {
        queries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            isFavorite = if (note.isFavorite) 1L else 0L,
            createdAt = note.createdAt
        )
    }

    suspend fun deleteNote(id: Long) {
        queries.deleteNote(id)
    }
}

private fun org.example.project.database.NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        isFavorite = isFavorite == 1L,
        createdAt = createdAt
    )
}
