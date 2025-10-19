package com.example.vest0

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.vest0.model.Usuario
import com.example.vest0.viewmodel.CatalogoScreen
import com.example.vest0.viewmodel.LoginScreen
import com.example.vest0.viewmodel.PerfilScreen
import com.example.vest0.viewmodel.RegistroScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VestoApp()
        }
    }
}

@Composable
fun VestoApp() {
    val currentScreen = remember { mutableStateOf("menu") }
    val usuarioRegistrado = remember { mutableStateOf<Usuario?>(null) }
    val usuarioLogeado = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Text(
            text = "VESTO",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when (currentScreen.value) {
                "menu" -> CentralContent("Menú Principal")
                "ropa" -> CatalogoScreen()
                "ubicacion" -> UbicacionScreen()
                "perfil" -> PerfilScreen(
                    usuario = usuarioRegistrado.value,
                    logeado = usuarioLogeado.value,
                    onIrLogin = { currentScreen.value = "login" },
                    onIrRegistro = { currentScreen.value = "registro" }
                )
                "registro" -> RegistroScreen { usuario ->
                    usuarioRegistrado.value = usuario
                    currentScreen.value = "perfil"
                }
                "login" -> LoginScreen(
                    usuarioRegistrado = usuarioRegistrado.value,
                    onLoginExitoso = {
                        usuarioLogeado.value = true
                        currentScreen.value = "perfil"
                    },
                    onIrRegistro = { currentScreen.value = "registro" }
                )
            }
        }

        BottomNavigationBar(
            currentScreen = currentScreen.value,
            onScreenChange = { currentScreen.value = it }
        )
    }
}

@Composable
fun CentralContent(text: String) {
    Text(text, fontSize = 20.sp, color = Color.Black)
}

@Composable
fun BottomNavigationBar(currentScreen: String, onScreenChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarButton("Ropa", R.drawable.ic_ropa_background, Modifier.weight(1f)) {
            onScreenChange("ropa")
        }
        BottomBarButton("Ubicación", R.drawable.ic_location_background, Modifier.weight(1f)) {
            onScreenChange("ubicacion")
        }
        BottomBarButton("Perfil", R.drawable.ic_profile_background, Modifier.weight(1f)) {
            onScreenChange("perfil")
        }
        BottomBarButton("Menú", R.drawable.ic_launcher_foreground, Modifier.weight(1f)) {
            onScreenChange("menu")
        }
    }
}

@Composable
fun BottomBarButton(text: String, iconRes: Int, modifier: Modifier, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = modifier.padding(vertical = 4.dp),
        colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text, fontSize = 12.sp)
        }
    }
}

@Composable
fun UbicacionScreen() {
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }
    var locationEnabled by remember { mutableStateOf(false) }
    var locationText by remember { mutableStateOf<String?>(null) }
    var requestLocation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        permissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = locationText ?: "Activa la ubicación para ver las tiendas cercanas.",
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))

        Button(onClick = { requestLocation = true }) {
            Text("Activar ubicación")
        }

        if (requestLocation) {
            UbicacionPermissionRequester { isGranted ->
                permissionGranted = isGranted
                if (isGranted) {
                    val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    locationEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                            lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                    if (!locationEnabled) {
                        Toast.makeText(context, "Por favor, activa los servicios de ubicación", Toast.LENGTH_SHORT).show()
                        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                }
                requestLocation = false
            }
        }
    }

    if (permissionGranted && locationEnabled) {
        LastKnownLocation { loc ->
            locationText = if (loc != null) {
                "Ubicación obtenida:\nLat: ${loc.latitude}\nLng: ${loc.longitude}"
            } else {
                "No se pudo obtener la ubicación. Inténtalo de nuevo."
            }
            locationEnabled = false
        }
    }
}

@Composable
fun UbicacionPermissionRequester(onResult: (Boolean) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
        }
        onResult(isGranted)
    }

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}

@Composable
fun LastKnownLocation(onLocation: (Location?) -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            val loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?: lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            onLocation(loc)
        } catch (_: SecurityException) {
            onLocation(null)
        }
    }
}


