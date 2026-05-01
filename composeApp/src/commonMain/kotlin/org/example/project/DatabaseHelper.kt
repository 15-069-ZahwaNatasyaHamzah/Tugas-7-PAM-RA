package org.example.project

import org.example.project.data.DatabaseDriverFactory
import org.example.project.data.NoteRepository
import org.example.project.database.NoteDatabase

class DatabaseHelper(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = NoteDatabase(databaseDriverFactory.createDriver())
    val repository = NoteRepository(database)
}
