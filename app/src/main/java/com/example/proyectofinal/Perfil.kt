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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun PantallaPerfilUsuario() {
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
                // Imagen de perfil
                Image(
                    painter = painterResource(id = R.drawable.user), // Cambia esto a tu imagen
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
                Text(text = "Categoria: Obesidad moribida")
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        // Botón para modificar datos
        Button(
            onClick = { /* Acción para modificar datos */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Modificar dato")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaPerfilUsuario() {
    MaterialTheme {
        PantallaPerfilUsuario()
    }
}
