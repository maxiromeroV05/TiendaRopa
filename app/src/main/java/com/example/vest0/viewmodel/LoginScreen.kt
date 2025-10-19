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
fun LoginScreen(
    usuarioRegistrado: Usuario?,
    onLoginExitoso: () -> Unit,
    onIrRegistro: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "INICIA SESIÓN",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(Modifier.height(32.dp))

        RegistroCampo(label = "EMAIL", value = email, onValueChange = { email = it })
        RegistroCampo(label = "CONTRASEÑA", value = contraseña, onValueChange = { contraseña = it })

        Text(
            text = "¿Has olvidado tu contraseña?",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(Modifier.height(32.dp))
        Button(
            onClick = {
                if (usuarioRegistrado != null &&
                    email == usuarioRegistrado.email &&
                    contraseña == usuarioRegistrado.contraseña
                ) {
                    onLoginExitoso()
                } else {
                    error = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("INICIAR SESIÓN", color = Color.White, fontSize = 14.sp)
        }

        Spacer(Modifier.height(8.dp))
        OutlinedButton(
            onClick = onIrRegistro,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("REGÍSTRATE", fontSize = 14.sp)
        }

        if (error) {
            Spacer(Modifier.height(8.dp))
            Text("Credenciales incorrectas", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
        }
    }
}