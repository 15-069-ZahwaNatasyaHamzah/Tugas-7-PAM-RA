package com.example.myprofileapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ProfileUiState(
    val name: String = "Zahwa Natasya Hamzah",
    val bio: String = "Mahasiswa Informatika ITERA",
    val email: String = "zahwa.123140069@student.itera.ac.id",
    val phone: String = "085229804644",
    val location: String = "Bandar Lampung",
    val isDarkMode: Boolean = false,
    val isEditing: Boolean = false
)

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun toggleDarkMode(enabled: Boolean) {
        _uiState.update { it.copy(isDarkMode = enabled) }
    }

    fun setEditing(editing: Boolean) {
        _uiState.update { it.copy(isEditing = editing) }
    }

    fun updateProfile(name: String, bio: String) {
        _uiState.update {
            it.copy(
                name = name,
                bio = bio,
                isEditing = false
            )
        }
    }
}
