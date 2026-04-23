package com.example.myprofileapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class NotesUiState(
    val notes: List<Note> = listOf(
        Note("1", "Tugas PAM", "Mengerjakan tugas navigasi MVVM"),
        Note("2", "Belanja", "Beli susu dan roti", isFavorite = true),
        Note("3", "Ide Project", "Aplikasi pencatat keuangan pribadi")
    )
)

class NotesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    fun addNote(title: String, content: String) {
        val newNote = Note(
            id = (System.currentTimeMillis()).toString(),
            title = title,
            content = content
        )
        _uiState.update { it.copy(notes = it.notes + newNote) }
    }

    fun updateNote(id: String, title: String, content: String) {
        _uiState.update { currentState ->
            val updatedNotes = currentState.notes.map {
                if (it.id == id) it.copy(title = title, content = content) else it
            }
            currentState.copy(notes = updatedNotes)
        }
    }

    fun deleteNote(id: String) {
        _uiState.update { currentState ->
            currentState.copy(notes = currentState.notes.filter { it.id != id })
        }
    }

    fun toggleFavorite(id: String) {
        _uiState.update { currentState ->
            val updatedNotes = currentState.notes.map {
                if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it
            }
            currentState.copy(notes = updatedNotes)
        }
    }

    fun getNoteById(id: String?): Note? {
        return uiState.value.notes.find { it.id == id }
    }
}
