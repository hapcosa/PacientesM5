package com.example.pacientesm4.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.pacientesm4.Models.Paciente
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.example.pacientesm4.utils.fromJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.single

private const val PREFERENCES_NAME = "pacientes"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class PacientesPreferencesImpl @Inject constructor(
    private val context: Context
) : PacientesPreferences {
    override suspend fun addPaciente(key: String, ListPaciente: List<Paciente>) {
        val json = Gson().toJson(ListPaciente)
        val preferenceKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = json
        }
    }

    override suspend fun getPaciente(key: String): Flow<List<Paciente>> {
        val gson = Gson()
        val preferenceKey = stringPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            val jsonString = preferences[preferenceKey] ?: "[]"
            gson.fromJson(jsonString, object : TypeToken<List<Paciente>>() {}.type)

        }

    }

    override suspend fun addFirstRun(key: String, value: Boolean) {
        val preferenceKey = booleanPreferencesKey(key)
        context.dataStore.edit {
            it[preferenceKey] = value
        }
    }

    override suspend fun getFirstRun(key: String): Flow<Boolean> {
        val preferenceKey = booleanPreferencesKey(key)
        return context.dataStore.data.map {
            it[preferenceKey]?: false
        }
    }

}