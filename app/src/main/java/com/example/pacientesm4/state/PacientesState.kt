package com.example.pacientesm4.state

import com.example.pacientesm4.Models.Paciente

data class PacientesState(
    val pacientes: List<Paciente> = emptyList(), )

data class IMC(
    val peso:String="",
    val edad:String="",
    val altura:String="",
    val genero:String="",
    val showAlert: Boolean = false,
    val IMC:String="",
    val estado:String=""
)