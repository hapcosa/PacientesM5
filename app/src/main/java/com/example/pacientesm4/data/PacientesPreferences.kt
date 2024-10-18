package com.example.pacientesm4.data


import com.example.pacientesm4.Models.Paciente
import kotlinx.coroutines.flow.Flow

interface PacientesPreferences {
    suspend fun addPaciente(key : String, ListPaciente: List<Paciente>)
    suspend fun getPaciente(key: String): Flow<List<Paciente>>
    suspend fun addFirstRun(key: String, value: Boolean)
    suspend fun getFirstRun(key: String):Flow<Boolean>
}