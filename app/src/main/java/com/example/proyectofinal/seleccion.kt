package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

class seleccion : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PantallaSeleccionObjetivo()
            }
        }
    }
}

@Composable
fun PantallaSeleccionObjetivo() {
    var showDialog by remember { mutableStateOf(false) }
    var nivelActividad by remember { mutableStateOf("") }
    var caloriasBase by remember { mutableStateOf(0) }
    var caloriasAjustadas by remember { mutableStateOf(0) }
    var caloriasExtras by remember { mutableStateOf(0) }
    var caloriasMostradas by remember { mutableStateOf("") }
    var isSaveEnabled by remember { mutableStateOf(false) }
    var areButtonsEnabled by remember { mutableStateOf(true) } // Para habilitar/deshabilitar los botones de subir/bajar
    var isUndoVisible by remember { mutableStateOf(false) } // Para mostrar el botón de deshacer

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF8F9FA)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selecciona tu objetivo",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Icono de pesa
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDialog = true },
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF77DD77))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Selecciona Objetivo de Peso",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Image(painter = painterResource(id = R.drawable.pesa), contentDescription = "Icono de pesa")
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Selecciona un objetivo de peso") },
                text = {
                    Column {
                        val objetivos = listOf("Bajar de Peso", "Mantener Peso", "Subir de Peso")
                        objetivos.forEach { objetivo ->
                            TextButton(onClick = {
                                nivelActividad = objetivo
                                showDialog = false
                                caloriasBase = when (objetivo) {
                                    "Bajar de Peso" -> 1500
                                    "Mantener Peso" -> 2200
                                    "Subir de Peso" -> 3000
                                    else -> 0
                                }
                                caloriasAjustadas = caloriasBase
                                caloriasExtras = 0 // Resetea calorías extra
                                caloriasMostradas = caloriasAjustadas.toString()
                                isSaveEnabled = true // Habilitar el botón de guardar al seleccionar un objetivo
                            }) {
                                Text(objetivo)
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Calorías recomendadas: ${caloriasMostradas.ifEmpty { "Seleccione un objetivo" }}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 20.dp)
        )

        if (caloriasBase > 0) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.elevatedCardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                content = {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Botón para subir calorías
                        Image(
                            painter = painterResource(id = R.drawable.arriba), // Reemplaza con tu imagen
                            contentDescription = "Botón para subir 50 calorías",
                            modifier = Modifier
                                .size(48.dp)
                                .clickable(enabled = areButtonsEnabled) { // Botón habilitado/deshabilitado según el estado
                                    if (caloriasExtras < 400) {
                                        caloriasExtras += 50
                                        caloriasAjustadas = caloriasBase + caloriasExtras
                                        caloriasMostradas = caloriasAjustadas.toString()
                                    }
                                    // Actualiza el estado del botón guardar
                                    isSaveEnabled = (caloriasAjustadas in (caloriasBase - 400)..(caloriasBase + 400))
                                }
                        )

                        Text(
                            text = "Calorías ajustadas: $caloriasAjustadas",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        // Botón para bajar calorías
                        Image(
                            painter = painterResource(id = R.drawable.abajo), // Reemplaza con tu imagen
                            contentDescription = "Botón para bajar 50 calorías",
                            modifier = Modifier
                                .size(48.dp)
                                .clickable(enabled = areButtonsEnabled) { // Botón habilitado/deshabilitado según el estado
                                    if (caloriasExtras > -400) {
                                        caloriasExtras -= 50
                                        caloriasAjustadas = caloriasBase + caloriasExtras
                                        caloriasMostradas = caloriasAjustadas.toString()
                                    }
                                    // Actualiza el estado del botón guardar
                                    isSaveEnabled = (caloriasAjustadas in (caloriasBase - 400)..(caloriasBase + 400))
                                }
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    caloriasMostradas = caloriasAjustadas.toString()
                    isSaveEnabled = false // Deshabilitar el botón de guardar
                    areButtonsEnabled = false // Deshabilitar los botones de ajuste
                    isUndoVisible = true // Mostrar el botón "Deshacer cambios"
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isSaveEnabled, // Habilitar o deshabilitar el botón según el estado
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text("Guardar Calorías Recomendadas")
            }

            if (isUndoVisible) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        caloriasExtras = 0 // Resetea las calorías extra
                        caloriasAjustadas = caloriasBase // Restaurar a las calorías base
                        caloriasMostradas = caloriasAjustadas.toString()
                        areButtonsEnabled = true // Rehabilitar los botones de ajuste
                        isSaveEnabled = true // Rehabilitar el botón de guardar
                        isUndoVisible = false // Ocultar el botón "Deshacer cambios"
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Deshacer cambios")
                }
            }
            if (isUndoVisible) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {

                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                ) {
                    Text("Avanzar")
                }
            }
        }
    }
}







