package com.example.pacientesm4.Models

class Imc{
    var altura: String = ""
    var peso: String = ""
    var genero: String = ""
    var edad: String = ""
    var IMC: String = ""
    var estado: String = ""

    constructor(
        altura: String,
        peso: String,
        genero: String,
        edad: String,
        IMC: String,

    ) {
        this.altura = altura
        this.peso = peso
        this.genero = genero
        this.edad = edad
        this.IMC = IMC

    }

    fun calcularImc(): Double {
        return peso.toDouble() / (altura.toDouble() * altura.toDouble())
    }

    fun Obesidad(): String {
        val imc = this.calcularImc()
        return if (imc < 18.5) "Bajo peso" else if (imc < 25) "Normal" else if (imc < 30) "Sobrepeso" else if (imc < 35) "Obesidad I" else if (imc < 40) "Obesidad II" else "Obesidad III"
    }

    override fun toString(): String {
        return Obesidad()
    }
    constructor(){}


}
