package com.example.myprofileapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

import com.example.myprofileapp.platform.DeviceInfo

data class ProfileUiState(
    val name: String = "Zahwa Natasya Hamzah",
    val bio: String = "Mahasiswa Informatika ITERA",
    val email: String = "zahwa.123140069@student.itera.ac.id",
    val phone: String = "085229804644",
    val location: String = "Bandar Lampung",
    val isDarkMode: Boolean = false,
    val isEditing: Boolean = false,
    val sortOrder: String = "DESC",
    val deviceModel: String = "",
    val deviceOs: String = ""
)

class ProfileViewModel(
    private val settingsRepository: SettingsRepository? = null,
    private val deviceInfo: DeviceInfo? = null
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState(
        deviceModel = deviceInfo?.model ?: "Unknown",
        deviceOs = deviceInfo?.osVersion ?: "Unknown"
    ))

    val uiState: StateFlow<ProfileUiState> = if (settingsRepository != null) {
        combine(
            _uiState,
            settingsRepository.isDarkMode,
            settingsRepository.sortOrder
        ) { state, dark, sort ->
            state.copy(isDarkMode = dark, sortOrder = sort)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)
    } else {
        _uiState.asStateFlow()
    }

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository?.setDarkMode(enabled)
            if (settingsRepository == null) {
                _uiState.update { it.copy(isDarkMode = enabled) }
            }
        }
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
