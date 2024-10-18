package com.example.pacientesm4.Models

class Paciente {
    var id:Int? = 0
    var nombre:String = ""
    var imc:Imc? = Imc()

    constructor(nombre: String, id: Int?, imc: Imc?) {
        this.nombre = nombre
        this.id = id
        this.imc = imc
    }
    constructor(){

    }
}