package com.example.myprofileapp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(private val dataStore: DataStore<Preferences>) {
    private object Keys {
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val SORT_ORDER = stringPreferencesKey("sort_order")
    }

    val isDarkMode: Flow<Boolean> = dataStore.data.map { it[Keys.IS_DARK_MODE] ?: false }
    val sortOrder: Flow<String> = dataStore.data.map { it[Keys.SORT_ORDER] ?: "DESC" }

    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { it[Keys.IS_DARK_MODE] = enabled }
    }

    suspend fun setSortOrder(order: String) {
        dataStore.edit { it[Keys.SORT_ORDER] = order }
    }
}
