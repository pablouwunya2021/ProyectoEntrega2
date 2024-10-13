package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

class CaloriasScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantallaCalorias()
        }
    }
}

@Composable
fun PantallaCalorias() {
    var caloriasIngresadas by remember { mutableStateOf(TextFieldValue("")) }
    var listaCalorias by remember { mutableStateOf(listOf<Int>()) } // Lista de calorías diarias
    var contadorDias by remember { mutableStateOf(1) } // Contador para los días visibles

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registro de Calorías",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 24.sp,
                color = Color.Black
            ),
            modifier = Modifier.padding(16.dp)
        )

        // Campo para ingresar calorías (solo números)
        BasicTextField(
            value = caloriasIngresadas,
            onValueChange = { caloriasIngresadas = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // Aceptar solo números
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para agregar calorías
        Button(
            onClick = {
                val calorias = caloriasIngresadas.text.toIntOrNull()
                if (calorias != null) {
                    if (listaCalorias.size >= 5) {
                        // Si hay más de 5 barras, elimina la primera
                        listaCalorias = listaCalorias.drop(1)
                        contadorDias++ // Incrementa el contador de días
                    }
                    listaCalorias = listaCalorias + calorias
                    caloriasIngresadas = TextFieldValue("") // Reinicia el campo
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Text("Agregar calorías del día")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Mostrar la gráfica de barras
        if (listaCalorias.isNotEmpty()) {
            Text(
                text = "Consumo diario de calorías",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    color = Color.Black
                ),
                modifier = Modifier.padding(16.dp)
            )

            BarChart(listaCalorias, contadorDias)
        } else {
            Text(
                text = "No hay datos de calorías ingresados aún.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Gray,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun BarChart(data: List<Int>, contadorDias: Int) {
    val maxCalorias = data.maxOrNull()?.toFloat() ?: 0f
    val barWidth = 40.dp
    val spacing = 16.dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color(0xFFDDDDDD)) // Fondo más oscuro
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val barSpacing = barWidth.toPx() + spacing.toPx()

            data.forEachIndexed { index, calorias ->
                val barHeight = (canvasHeight * (calorias / maxCalorias)).toFloat()

                // Dibuja las barras
                drawRoundRect(
                    color = Color.Blue,
                    topLeft = androidx.compose.ui.geometry.Offset(x = index * barSpacing, y = canvasHeight - barHeight),
                    size = Size(barWidth.toPx(), barHeight),
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Etiquetas de día debajo de cada barra
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            modifier = Modifier.fillMaxWidth()
        ) {
            data.forEachIndexed { index, _ ->
                Text(
                    text = "Día ${contadorDias + index}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.width(barWidth),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaCaloriasPreview() {
    PantallaCalorias()
}





