package com.example.pacientesm4.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.pacientesm4.viewsModels.ViewModel
import com.example.pacientesm4.Models.Paciente

@Composable
fun CardHorizontal(paciente: Paciente, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Text(paciente.nombre, style = MaterialTheme.typography.titleLarge)
                space(3.dp)
                paciente.imc?.let { Text("edad: ${it.edad}", style = MaterialTheme.typography.titleLarge)
                space(3.dp)}
                paciente.imc?.let { Text("Imc: ${it.IMC}", style = MaterialTheme.typography.titleLarge)
                space(3.dp)}
                paciente.imc?.let { Text("Estado: ${it.estado}", style = MaterialTheme.typography.titleLarge)
                space(3.dp)}


            }

            Button( onClick = { navController.navigate("imc/${paciente.id}") }) {
                Text("Calcular Imc",style = MaterialTheme.typography.titleLarge)
            }
        }

    }
}


@Composable
fun addPacienteModal(
    title: String,
    message: String,
    confirmText: String,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    viewModel: ViewModel
) {
    val scroll = rememberScrollState(0)
    var paciente = Paciente()
    var name by remember { mutableStateOf("") }
    Dialog(onDismissRequest ={onDismissClick()},
        content = {
            Column(){
                Text(text ="Agregar Paciente" )
                OutlinedTextField(value = name, onValueChange = {name= it})
                Button(
                    onClick = { onConfirmClick()
                        paciente.nombre = name
                        viewModel.addPaciente(paciente)}
                ) {
                    Text(text = confirmText)
                }
            }


        })



}

@Composable
fun space(size: Dp){
    Spacer(modifier = Modifier.height(size))
}
@Composable
fun MainTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
){

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun multibutton(viewModel: ViewModel){
    var state = viewModel.state.genero
    val checkedList = remember { mutableStateListOf<Int>() }
    val listoptions= listOf("Hombre","Mujer")
    var changeCheked= remember {false }
    state = if (changeCheked) {
        "Hombre"
    } else {
        "Mujer"
    }
    LaunchedEffect(checkedList){
    }
    MultiChoiceSegmentedButtonRow{
        listoptions.forEachIndexed{index,label->
            SegmentedButton(checked=index in checkedList, onCheckedChange = {
                if (index in checkedList) {
                    checkedList.remove(index)
                } else {
                    checkedList.add(index)
                    checkedList.remove(index-1)
                    checkedList.remove(index+1)
                }
            }, shape = SegmentedButtonDefaults.itemShape(index = index, count = listoptions.size) ) {
                Text(label)
            }
        }
    }

}
@Composable
fun resultText(text:String){
    Text(text = text, color = Color.Black, fontSize = 30.sp)
}
@Composable
fun MainButton(
    text: String,
    color: Color = MaterialTheme.colorScheme.primary,
    onClick:() -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = color,
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    ) {
        Text(
            text = text,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Alert(
    title: String,
    msj: String,
    confirmText: String,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissClick,
        title = { Text(text = title) },
        text = { Text(text = msj) },
        shape = CutCornerShape(10.dp),
        confirmButton = {
            Button(onClick = onConfirmClick) {
                Text(text = confirmText)
            }
        }


    )
}