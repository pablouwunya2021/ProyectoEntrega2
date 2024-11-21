package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlin.math.pow

class PantallaaIMC : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            PantallaIMC(navController = navController)
        }
    }
}

@Composable
fun PantallaIMC(navController: NavController) {
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var nivelActividad by remember { mutableStateOf("Seleccione un nivel") }
    var resultadoIMC by remember { mutableStateOf("") }
    var categoriaIMC by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cálculo de IMC",
            style = MaterialTheme.typography.headlineLarge
        )

        // Campo de entrada de peso
        OutlinedTextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text("Peso (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Campo de entrada de altura
        OutlinedTextField(
            value = altura,
            onValueChange = { altura = it },
            label = { Text("Altura (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Campo de entrada de edad
        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Botón para abrir el diálogo de selección de nivel de actividad
        TextButton(onClick = { showDialog = true }) {
            Text("Nivel de actividad física: $nivelActividad")
        }

        // Diálogo para seleccionar nivel de actividad
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Selecciona un nivel de actividad") },
                text = {
                    Column {
                        listOf("Nula", "Poca", "Media", "Alta").forEach { nivel ->
                            TextButton(onClick = {
                                nivelActividad = nivel
                                showDialog = false
                            }) {
                                Text(nivel)
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

        // Botón de cálculo IMC
        Button(
            onClick = {
                if (peso.isNotEmpty() && altura.isNotEmpty()) {
                    val imc = peso.toFloat() / (altura.toFloat() / 100).pow(2)
                    resultadoIMC = "Tu IMC es %.2f".format(imc)
                    categoriaIMC = when {
                        imc < 18.5 -> "Peso bajo"
                        imc < 24.9 -> "Peso ideal"
                        imc < 29.9 -> "Sobrepeso"
                        else -> "Obesidad"
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Calcular IMC")
        }

        if (resultadoIMC.isNotEmpty()) {
            Text(text = resultadoIMC, style = MaterialTheme.typography.titleMedium)
            Text(text = "Categoría: $categoriaIMC", style = MaterialTheme.typography.titleMedium)

            // Mostrar imágenes según la categoría
            when (categoriaIMC) {
                "Peso bajo" -> Image(painter = painterResource(id = R.drawable.pesopluma), contentDescription = "Peso bajo")
                "Peso ideal" -> Image(painter = painterResource(id = R.drawable.messi), contentDescription = "Peso ideal")
                "Sobrepeso" -> Image(painter = painterResource(id = R.drawable.homero), contentDescription = "Sobrepeso")
                "Obesidad" -> Image(painter = painterResource(id = R.drawable.bart), contentDescription = "Obesidad")
            }

            // Botón "Cambiar de objetivo" que aparece después del cálculo de IMC
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.navigate("PantallaSeleccionObjetivo")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
            ) {
                Text("Cambiar de objetivo?")
            }
        }
    }
}








