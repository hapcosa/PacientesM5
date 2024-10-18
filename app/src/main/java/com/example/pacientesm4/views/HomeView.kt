package com.example.pacientesm4.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pacientesm4.R

import com.example.pacientesm4.component.CardHorizontal
import com.example.pacientesm4.component.TopBar
import com.example.pacientesm4.viewsModels.ViewModel
import com.example.pacientesm4.component.addPacienteModal

@Composable
fun HomeView(navController: NavController, viewModel: ViewModel = hiltViewModel()) {
    var isModalOpen by remember { mutableStateOf(false) }
    viewModel.addFirstRun(false)
    Scaffold(
        topBar = { TopBar(stringResource(R.string.Home), navController) },
        content = { ContentHomeView(it, navController, viewModel, isModalOpen) },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.showAddPaciente = true }) {
                Icon(Icons.Filled.AddCircle, contentDescription = "add")
            }

        }
    )
}

@Composable
fun ContentHomeView(
    itPadd: PaddingValues,
    navController: NavController,
    viewModel: ViewModel,
    isModalOpen: Boolean,
) {
    val state = viewModel.statePaciente.value.pacientes

    Column(
        modifier = androidx.compose.ui.Modifier.padding(itPadd)
    ) {

        LazyColumn(Modifier.padding()) {
            items(state) {
                CardHorizontal(it, navController)
            }

        }
    }
    if(viewModel.showAddPaciente){
        addPacienteModal(title ="Agregar paciente", message ="Agrega paciente" , confirmText = "Agregar", onConfirmClick = { viewModel.openCloseModal() }, onDismissClick = { viewModel.openCloseModal() }, viewModel)
    }


}

