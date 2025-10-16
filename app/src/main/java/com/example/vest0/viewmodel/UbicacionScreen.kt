import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun UbicacionScreen() {
    // Estado para gestionar el flujo de la UI.
    var permissionGranted by remember { mutableStateOf(false) }
    var locationEnabled by remember { mutableStateOf(false) }
    var locationText by remember { mutableStateOf<String?>(null) }

    // Estado para controlar cuándo iniciar el proceso de solicitud.
    var requestLocation by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Comprobamos el estado inicial de los permisos y la ubicación activada
    // cuando el composable entra en la composición por primera vez.
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
        // Mostramos el estado actual o la ubicación obtenida.
        Text(
            text = locationText ?: "Activa la ubicación para ver las tiendas cercanas.",
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))

        // El botón inicia la secuencia de solicitudes.
        Button(onClick = { requestLocation = true }) {
            Text("Activar ubicación")
        }

        // Si el usuario hace clic en el botón, iniciamos el proceso.
        if (requestLocation) {
            // 1. Solicitar permiso de ubicación.
            UbicacionPermissionRequester(
                onResult = { isGranted ->
                    permissionGranted = isGranted
                    if (isGranted) {
                        // Si se concede el permiso, comprobamos si la ubicación está activada.
                        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                        val isEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                        locationEnabled = isEnabled

                        if (!isEnabled) {
                            // Si no está activada, llevamos al usuario a la configuración.
                            Toast.makeText(context, "Por favor, activa los servicios de ubicación", Toast.LENGTH_SHORT).show()
                            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                        }
                    }
                    // Reiniciamos el disparador para no volver a pedirlo en cada recomposición.
                    requestLocation = false
                }
            )
        }
    }

    // 2. Si ya tenemos permiso y la ubicación está activada, obtenemos la localización.
    // LaunchedEffect se ejecutará cada vez que permissionGranted o locationEnabled cambien a true.
    if (permissionGranted && locationEnabled) {
        LastKnownLocation { loc ->
            if (loc != null) {
                locationText = "Ubicación obtenida:\nLat: ${loc.latitude}\nLng: ${loc.longitude}"
            } else {
                locationText = "No se pudo obtener la ubicación. Inténtalo de nuevo."
            }
            // Marcamos la ubicación como ya no activada para no volver a buscarla
            // hasta que el usuario lo pida de nuevo.
            locationEnabled = false
        }
    }
}

@Composable
fun UbicacionPermissionRequester(onResult: (isGranted: Boolean) -> Unit) {
    val context = LocalContext.current
    // Creamos el lanzador que solicitará el permiso.
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // Cuando el usuario responde, usamos el callback normal (no Composable).
        if (!isGranted) {
            Toast.makeText(context, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
        }
        onResult(isGranted)
    }

    // Usamos LaunchedEffect para lanzar la petición una sola vez.
    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}

@Composable
fun LastKnownLocation(onLocation: (Location?) -> Unit) {
    val context = LocalContext.current

    // Este LaunchedEffect se ejecuta una sola vez para obtener la ubicación.
    // La anotación @SuppressLint es para que Android Studio no se queje
    // del permiso, ya que estamos seguros de que lo hemos pedido antes.
    LaunchedEffect(Unit) {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            val lastKnownLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?: lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            onLocation(lastKnownLocation)
        } catch (_: SecurityException) {
            // Si algo sale mal (muy raro si el permiso fue concedido), devolvemos null.
            onLocation(null)
        }
    }
}


