package com.example.vest0.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {
    companion object {
        val EMAIL_KEY = stringPreferencesKey("email")
    }

    val getEmail: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[EMAIL_KEY]
    }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit { prefs ->
            prefs[EMAIL_KEY] = email
        }
    }

    suspend fun clearEmail() {
        context.dataStore.edit { prefs ->
            prefs.remove(EMAIL_KEY)
        }
    }
}