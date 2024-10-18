package com.example.pacientesm4.viewsModels


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


import com.example.pacientesm4.Models.Paciente
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.pacientesm4.Models.Imc
import com.example.pacientesm4.data.PacientesPreferences
import com.example.pacientesm4.state.IMC


import com.example.pacientesm4.state.PacientesState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class ViewModel @Inject constructor(
    private val repo: PacientesPreferences
) : ViewModel() {
    var statePaciente = mutableStateOf(PacientesState())
        private set
    var state by mutableStateOf(IMC())
        private set
    var showAddPaciente by mutableStateOf(false)

    private var firstRun by mutableStateOf(true)

    init {
        viewModelScope.launch {
            repo.getPaciente("pacientes").collect() {
                statePaciente.value = statePaciente.value.copy(pacientes = it)
            }
            repo.getFirstRun("firstRun").collect() {
                firstRun = it
            }
        }

    }

    fun addPaciente(paciente: Paciente) {
        paciente.id = statePaciente.value.pacientes.size + 1
        var list: List<Paciente>
        list = statePaciente.value.pacientes.toMutableList()
        list.add(paciente)
        viewModelScope.launch {
            repo.addPaciente("pacientes", list)

        }

    }

    fun getPacienteId(id: Int): Paciente? {
        return statePaciente.value.pacientes.find { it.id == id }
    }

    fun editPaciente(pacienteId: Int, imc: Imc) {
        var pacienteOld = getPacienteId(pacienteId)
        var list: List<Paciente>
        list = statePaciente.value.pacientes.toMutableList()
        list.remove(pacienteOld)
        var pacientenew = pacienteOld?.let { Paciente(it.nombre, pacienteOld.id, imc) }
        imc.estado = imc.Obesidad()
        pacientenew?.let { list.add(it) }
        viewModelScope.launch {
            repo.addPaciente("pacientes", list)
        }


    }

    fun openCloseModal() {
        showAddPaciente = !showAddPaciente
    }

    fun onValue(value: String, text: String) {
        when (text) {
            "peso" -> {
                state = state.copy(peso = value)
            }
            "edad" -> state = state.copy(edad = value)
            "altura" -> state = state.copy(altura = value)
        }
    }

    fun calcularImc(): String {
        val peso = state.peso
        val edad = state.edad
        val altura = state.altura
        var imc = 0.0
        if (peso != "" && altura != "") {
            imc = peso.toDouble() / (altura.toDouble() * altura.toDouble())
            state = state.copy(IMC = imc.roundToInt().toString())
        } else {
            state = state.copy(
                showAlert = true
            )
        }
        return imc.toString()
    }

    fun limpiar() {
        state = state.copy(
            peso = "",
            edad = "",
            altura = "",
            IMC = ""
        )
    }

    fun cancelAlert() {
        state = state.copy(
            showAlert = false
        )
    }

    fun getFirstRunView(): Boolean {
        return firstRun
    }
    fun addFirstRun(value: Boolean){
        viewModelScope.launch {
            repo.addFirstRun("firstRun", value)
        }

    }
}