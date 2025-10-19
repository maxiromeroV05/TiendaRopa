package com.example.vest0.viewmodel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vest0.model.Usuario

@Composable
fun PerfilScreen(
    usuario: Usuario?,
    logeado: Boolean,
    onIrLogin: () -> Unit,
    onIrRegistro: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextButton(onClick = onIrLogin) {
                Text("INICIAR SESIÓN", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
            TextButton(onClick = onIrRegistro) {
                Text("REGÍSTRATE", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text("PERFIL DE USUARIO", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(32.dp))

            if (logeado && usuario != null) {
                RegistroCampo(label = "EMAIL", value = usuario.email, onValueChange = {})
                RegistroCampo(label = "NOMBRE", value = usuario.nombre, onValueChange = {})
                RegistroCampo(label = "APELLIDOS", value = usuario.apellidos, onValueChange = {})
            } else {
                Text("No has iniciado sesión.", fontSize = 16.sp)
            }
        }
    }
}
