package com.arkhe.pinlock.data.local.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PinDataStore(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("pin_preferences")
        private val PIN_CREATED_KEY = booleanPreferencesKey("pin_created")
        private val PIN_CODE_KEY = stringPreferencesKey("pin_code")
        private val SIGNED_IN_KEY = booleanPreferencesKey("signed_in")
    }

    suspend fun savePinCreated(isCreated: Boolean) {
        Log.d("PinDataStore", "Saving PIN created: $isCreated")
        context.dataStore.edit { preferences ->
            preferences[PIN_CREATED_KEY] = isCreated
        }
    }

    suspend fun savePinCode(pinCode: String) {
        Log.d("PinDataStore", "Saving PIN code: ${pinCode.length} digits")
        context.dataStore.edit { preferences ->
            preferences[PIN_CODE_KEY] = pinCode
        }
    }

    suspend fun saveSignInStatus(isSignedIn: Boolean) {
        Log.d("PinDataStore", "Saving sign in status: $isSignedIn")
        context.dataStore.edit { preferences ->
            preferences[SIGNED_IN_KEY] = isSignedIn
        }
    }

    val pinCreatedFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        val value = preferences[PIN_CREATED_KEY] ?: false
        Log.d("PinDataStore", "Reading PIN created: $value")
        value
    }

    val pinCodeFlow: Flow<String> = context.dataStore.data.map { preferences ->
        val value = preferences[PIN_CODE_KEY] ?: ""
        Log.d("PinDataStore", "Reading PIN code: ${value.length} digits")
        value
    }

    val signInStatusFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        val value = preferences[SIGNED_IN_KEY] ?: false
        Log.d("PinDataStore", "Reading sign in status: $value")
        value
    }
}