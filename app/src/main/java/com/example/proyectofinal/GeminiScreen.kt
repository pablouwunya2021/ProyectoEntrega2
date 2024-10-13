package com.example.proyectofinal

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class GeminiScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PantallaGemini()
            }
        }
    }
}

@Composable
fun PantallaGemini() {
    var calorias by remember { mutableStateOf("") }
    var isScanning by remember { mutableStateOf(false) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) } // Aquí se almacenará la imagen capturada
    val context = LocalContext.current

    // Iniciar la cámara y capturar la foto
    val openCamera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { image ->
        image?.let {
            bitmap = it // Guardar la imagen capturada
            isScanning = true // Cambiar el estado para mostrar las calorías detectadas
            calorias = "200" // Aquí podrías hacer el cálculo real de calorías
        }
    }

    // Solicitar permisos de cámara
    val requestCameraPermission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            openCamera.launch(null) // Iniciar la cámara
        } else {
            println("Permiso de cámara denegado")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFFFFFFF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Escaneo de alimentos",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Imagen central, si se ha capturado una foto la mostramos, sino el ícono
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color(0xFF333333)),
            contentAlignment = Alignment.Center
        ) {
            bitmap?.let { // Si hay una imagen, la mostramos
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Imagen capturada",
                    modifier = Modifier.fillMaxSize()
                )
            } ?: run { // Si no, mostramos el ícono original
                Image(
                    painter = painterResource(id = R.drawable.gemini),
                    contentDescription = "Ícono de escaneo"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Empezar escaneo",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 20.sp
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para empezar el escaneo
        Button(
            onClick = {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    openCamera.launch(null) // Abrir cámara
                } else {
                    requestCameraPermission.launch(Manifest.permission.CAMERA) // Pedir permiso
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.escaner), // Reemplaza con el ícono de cámara
                    contentDescription = "Ícono de cámara",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Escanear")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Texto de descripción
        Text(
            text = "Escanee sus alimentos con la cámara trasera, se hará un cálculo aproximado de sus calorías ingeridas",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Black,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Mostrar las calorías detectadas si se está escaneando
        if (isScanning) {
            Text(
                text = "Calorías detectadas: $calorias",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp
                )
            )
        }

        // Texto adicional bajo el botón después de capturar la imagen
        if (bitmap != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Imagen capturada exitosamente (se agregara una descripción de los alimentos cuando se integre GEMINI)",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Black,
                    fontSize = 16.sp
                )
            )
        }
    }
}




