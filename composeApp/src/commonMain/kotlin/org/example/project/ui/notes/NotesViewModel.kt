package org.example.project.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.project.data.NoteRepository
import org.example.project.domain.Note

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val favorites: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)

class NotesViewModel(private val repository: NoteRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            combine(
                repository.getAllNotes(),
                repository.getFavoriteNotes()
            ) { notes, favorites ->
                _uiState.update { it.copy(
                    notes = notes,
                    favorites = favorites,
                    isLoading = false
                ) }
            }.collect()
        }
    }

    fun searchNotes(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        viewModelScope.launch {
            if (query.isBlank()) {
                loadNotes()
            } else {
                repository.searchNotes(query).collect { notes ->
                    _uiState.update { it.copy(notes = notes) }
                }
            }
        }
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insertNote(Note(title = title, content = content))
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }

    fun toggleFavorite(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note.copy(isFavorite = !note.isFavorite))
        }
    }
}
