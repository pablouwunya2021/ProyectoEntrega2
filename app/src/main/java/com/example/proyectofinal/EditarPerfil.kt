package com.example.proyectofinal


import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FieldValue

class EditProfileScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PantallaEditarPerfil()
            }
        }
    }
}

@Composable
fun PantallaEditarPerfil() {
    var nombre by remember { mutableStateOf("Pablo Cabrera") }
    var edad by remember { mutableStateOf(TextFieldValue("19")) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isCameraPermissionGranted by remember { mutableStateOf(false) }

    // Solicitar permisos de cámara
    val requestCameraPermission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        isCameraPermissionGranted = isGranted
    }

    // Iniciar la cámara y capturar la foto
    val openCamera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { image ->
        bitmap = image // Guardar la imagen capturada
    }

    // Comprobar permisos al abrir la pantalla
    if (ContextCompat.checkSelfPermission(LocalContext.current, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
        requestCameraPermission.launch(Manifest.permission.CAMERA)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFEFEFEF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sección de la cámara
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color(0xFFB0BEC5))
                .border(4.dp, Color.Gray)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Imagen del usuario",
                    modifier = Modifier.fillMaxSize()
                )
            } ?: run {
                // Aquí puedes mostrar un ícono de cámara si no hay imagen
                Image(
                    painter = painterResource(id = R.drawable.camara), // Cambia esto a tu ícono de cámara
                    contentDescription = "Icono de cámara"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para tomar foto
        Button(
            onClick = {
                if (isCameraPermissionGranted) {
                    openCamera.launch(null)
                } else {
                    requestCameraPermission.launch(Manifest.permission.CAMERA)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Tomar Foto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para el nombre
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de texto para la edad (solo números)
        TextField(
            value = edad,
            onValueChange = {
                if (it.text.all { char -> char.isDigit() }) {
                    edad = it // Permitir solo números
                }
            },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para calcular IMC
        Button(
            onClick = { /* Acción para volver a calcular el IMC */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Volver a calcular el IMC")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón para guardar cambios
        Button(
            onClick = { /* Acción para guardar cambios */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar cambios")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón para deshacer cambios
        Button(
            onClick = { /* Acción para deshacer cambios */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent) // Fondo transparente
        ) {
            Text(text = "Deshacer cambios", color = Color.Red)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaEditarPerfil() {
    MaterialTheme {
        PantallaEditarPerfil()
    }
}

