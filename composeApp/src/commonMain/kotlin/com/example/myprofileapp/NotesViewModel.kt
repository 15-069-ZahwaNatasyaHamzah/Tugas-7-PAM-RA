package com.example.myprofileapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface NotesUiState {
    data object Loading : NotesUiState
    data class Success(
        val notes: List<Note>,
        val searchQuery: String = ""
    ) : NotesUiState
    data class Empty(val message: String) : NotesUiState
}

class NotesViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    
    val uiState: StateFlow<NotesUiState> = combine(
        _searchQuery,
        repository.getAllNotes()
    ) { query, notes ->
        val filtered = if (query.isBlank()) notes else {
            notes.filter { it.title.contains(query, ignoreCase = true) || it.content.contains(query, ignoreCase = true) }
        }
        
        if (filtered.isEmpty()) {
            if (query.isBlank()) NotesUiState.Empty("Belum ada catatan")
            else NotesUiState.Empty("Tidak ada catatan yang cocok dengan '$query'")
        } else {
            NotesUiState.Success(filtered, query)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NotesUiState.Loading)

    fun onSearch(query: String) {
        _searchQuery.value = query
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insertNote(Note(
                id = System.currentTimeMillis().toString(),
                title = title,
                content = content
            ))
        }
    }

    fun updateNote(id: String, title: String, content: String) {
        viewModelScope.launch {
            val existing = repository.getNoteById(id)
            repository.insertNote(existing?.copy(title = title, content = content) 
                ?: Note(id, title, content))
        }
    }

    fun deleteNote(id: String) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }

    fun toggleFavorite(id: String) {
        viewModelScope.launch {
            repository.toggleFavorite(id)
        }
    }

    suspend fun getNoteById(id: String): Note? = repository.getNoteById(id)
}
