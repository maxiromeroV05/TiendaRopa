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
import com.example.vest0.datastore.UserPreferences
import com.example.vest0.model.Usuario
import com.example.vest0.repository.UsuarioRepositorySQLite
import kotlinx.coroutines.launch

@Composable
fun PerfilScreen(
    usuario: Usuario?,
    logeado: Boolean,
    onIrLogin: () -> Unit,
    onIrRegistro: () -> Unit,
    onCerrarSesion: () -> Unit,
    onEliminarUsuario: () -> Unit
) {
    val context = LocalContext.current
    val repo = remember { UsuarioRepositorySQLite(context) }
    val prefs = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
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
                RegistroCampo(label = "APELLIDOS", value = usuario.apellido, onValueChange = {})

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        val eliminado = repo.eliminar(usuario.email)
                        if (eliminado) {
                            Toast.makeText(context, "Usuario eliminado", Toast.LENGTH_SHORT).show()
                            scope.launch {
                                prefs.clearEmail()
                                onEliminarUsuario()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7)) // rojo elegante
                ) {
                    Text("ELIMINAR CUENTA", color = Color.White)
                }

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        scope.launch {
                            prefs.clearEmail()
                            onCerrarSesion()
                        }
                        Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black) // negro total
                ) {
                    Text("CERRAR SESIÓN", color = Color.White)
                }
            } else {
                Text("No has iniciado sesión.", fontSize = 16.sp)
            }
        }
    }
}