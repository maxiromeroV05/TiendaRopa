package com.example.vest0.viewmodel

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vest0.model.Usuario
import com.example.vest0.repository.UsuarioRepositorySQLite

@Composable
fun RegistroScreen(onRegistroExitoso: (Usuario) -> Unit) {
    val context = LocalContext.current
    val repo = remember { UsuarioRepositorySQLite(context) }

    var email by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "DATOS PERSONALES",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(Modifier.height(32.dp))

        RegistroCampo(label = "EMAIL", value = email, onValueChange = { email = it })
        RegistroCampo(label = "CONTRASEÑA", value = contraseña, onValueChange = { contraseña = it })
        RegistroCampo(label = "NOMBRE", value = nombre, onValueChange = { nombre = it })
        RegistroCampo(label = "APELLIDOS", value = apellido, onValueChange = { apellido = it })

        Spacer(Modifier.height(32.dp))
        Button(
            onClick = {
                if (email.isNotBlank() && contraseña.isNotBlank() && nombre.isNotBlank() && apellido.isNotBlank()) {
                    val nuevoUsuario = Usuario(email, contraseña, nombre, apellido, perfilUser = "usuario")
                    val exito = repo.insertar(nuevoUsuario)
                    if (exito) {
                        Toast.makeText(context, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                        onRegistroExitoso(nuevoUsuario)
                    } else {
                        Toast.makeText(context, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("CREAR CUENTA", color = Color.White, fontSize = 14.sp)
        }
    }
}

@Composable
fun RegistroCampo(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.Light, color = Color.Gray)
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(Modifier.height(16.dp))
    }
}