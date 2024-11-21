package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppMainScreen()
        }
    }
}

// Función principal que contiene la barra de navegación y la navegación entre pantallas
@Composable
fun AppMainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavigationGraph(navController, Modifier.padding(innerPadding))
    }
}

// Barra de navegación simple sin iconos, solo con los nombres de las pantallas
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf("Perfil", "Calorias", "Scan") // Solo la pantalla de perfil
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    BottomNavigation {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Spacer(modifier = Modifier.size(0.dp)) }, // Espacio vacío para icono
                label = { Text(screen) },
                selected = currentRoute == screen,
                onClick = {
                    navController.navigate(screen) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

// Función que gestiona la navegación entre pantallas
@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = "Perfil", modifier = modifier) {
        composable("Perfil") { PantallaPerfilUsuario(navController) }
        composable("Calorias") { PantallaCalorias() }
        composable("Scan") { PantallaGemini() }
        composable(route = "EditarPerfil") { PantallaEditarPerfil(navController = navController) }
        composable(route = "IMC") { PantallaIMC(navController = navController) }
        composable("PantallaSeleccionObjetivo") { PantallaSeleccionObjetivo() }

        // Usa la pantalla existente
    }
}

// Pantalla de perfil del usuario
@Composable
fun PantallaPerfilUsuario(navController: NavHostController) {
    val backgroundColor = Color(0xFFEFEFEF) // Color de fondo
    val cardColor = Color(0xFFB0BEC5) // Color de la tarjeta

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(25.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "Perfil del usuario",
                    modifier = Modifier
                        .size(128.dp)
                        .clip(MaterialTheme.shapes.large)
                        .border(6.dp, Color.Gray, MaterialTheme.shapes.large)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Pablo Cabrera",
                    fontWeight = FontWeight.Bold,
                    fontSize = 34.sp
                )
                Text(text = "Edad: 19 años")
                Text(text = "Peso: 95 kg")
                Text(text = "IMC: 30.8")
                Text(text = "Categoría: Obesidad mórbida")
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        // Botón que navega a PantallaEditarPerfil
        Button(
            onClick = { navController.navigate("EditarPerfil") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Modificar dato")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPantallaPerfilUsuario() {
    AppMainScreen()
}


