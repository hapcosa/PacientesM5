package com.example.pacientesm4.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pacientesm4.Models.Imc
import com.example.pacientesm4.R
import com.example.pacientesm4.component.Alert
import com.example.pacientesm4.component.MainButton
import com.example.pacientesm4.component.MainTextField
import com.example.pacientesm4.component.TopBar
import com.example.pacientesm4.component.multibutton
import com.example.pacientesm4.component.resultText
import com.example.pacientesm4.component.space
import com.example.pacientesm4.viewsModels.ViewModel
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ImcView(navController: NavController, viewModel: ViewModel, id: Int){
    Scaffold(
        topBar = { TopBar(stringResource(R.string.Imc), navController) },
        content = { ContentImcView(navController, viewModel, id) }
    )
}

@Composable
fun ContentImcView(navController: NavController, viewModel: ViewModel, id: Int) {
    var Imc = remember { mutableStateOf(Imc()) }
    var flagEstado=remember{mutableStateOf(false)}
    var state =viewModel.state
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        resultText(state.IMC + " IMC")
        multibutton(viewModel)
        space(size = 10.dp)
        MainTextField(
            value = state.peso,
            onValueChange = { viewModel.onValue(it, "peso") },
            label = "Peso"
        )
        space(size = 10.dp)
        MainTextField(
            value = state.altura,
            onValueChange = {viewModel.onValue(it, "altura") },
            label = "Altura"
        )
        space(size = 10.dp)
        MainTextField(
            value = state.edad,
            onValueChange = { viewModel.onValue(it, "edad") },
            label = "Edad"
        )
        space(size = 10.dp)
        MainButton(text = "Calcular") {

            viewModel.calcularImc()
            Imc.value = Imc(state.altura, state.peso, state.genero, state.edad, state.IMC)
            flagEstado.value=true
        }
        space(size = 10.dp)
        MainButton(text = "Guardar") {
            viewModel.calcularImc()
            Imc.value = Imc(state.altura, state.peso, state.genero, state.edad, state.IMC)
            viewModel.editPaciente(id, Imc.value)
            navController.navigate("Home")
        }
        space(size = 10.dp)
        if(flagEstado.value){
            Text("Estado: ${Imc.value.Obesidad()}")
            space(size = 10.dp)
        }

        MainButton(text = "Limpiar", color = MaterialTheme.colorScheme.error) {
            viewModel.limpiar()
        }
        if (state.showAlert) {
            Alert(
                title = "Alerta",
                msj = "ingrese datos",
                confirmText = "Aceptar",
                onConfirmClick = {viewModel.cancelAlert() }
            ) { }
        }

    }
}
