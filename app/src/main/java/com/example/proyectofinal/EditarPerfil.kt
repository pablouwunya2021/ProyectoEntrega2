package com.example.proyectofinal

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class EditProfileScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavGraph(navController = navController)
        }
    }
}

// Configuración de navegación
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "PantallaEditarPerfil") {
        composable("PantallaEditarPerfil") { PantallaEditarPerfil(navController) }
        composable("IMC") { PantallaIMC(navController) } // Navegación a PantallaIMC
    }
}

@Composable
fun PantallaEditarPerfil(navController: NavHostController) {
    var nombre by remember { mutableStateOf("Pablo Cabrera") }
    var edad by remember { mutableStateOf(TextFieldValue("19")) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isCameraPermissionGranted by remember { mutableStateOf(false) }

    val requestCameraPermission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        isCameraPermissionGranted = isGranted
    }

    val openCamera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { image ->
        bitmap = image
    }

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
                Image(
                    painter = painterResource(id = R.drawable.camara),
                    contentDescription = "Icono de cámara"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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

        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = edad,
            onValueChange = {
                if (it.text.all { char -> char.isDigit() }) {
                    edad = it
                }
            },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para navegar a PantallaIMC
        Button(
            onClick = { navController.navigate("IMC") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Volver a calcular el IMC")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { /* Acción para guardar cambios */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar cambios")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { /* Acción para deshacer cambios */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Text(text = "Deshacer cambios", color = Color.Red)
        }
    }
}




