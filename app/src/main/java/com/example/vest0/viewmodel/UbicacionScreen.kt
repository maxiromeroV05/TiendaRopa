package com.example.vest0.viewmodel

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun UbicacionScreen() {
    val miUbicacion = LatLng(-33.49936500787212, -70.61654033901539)

    val lugares = listOf(
        "Tienda 1" to LatLng(-33.497672632070476, -70.6126025410391),
        "Tienda 2" to LatLng(-33.50104607891704, -70.61707122623334),
        "Tienda 3" to LatLng(-33.49774554586376, -70.6178305190539)
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(miUbicacion, 15f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(), // ✅ pantalla completa
        cameraPositionState = cameraPositionState
    ) {
        lugares.forEach { (nombre, posicion) ->
            Marker(
                state = MarkerState(position = posicion),
                title = nombre
            )
        }
    }
}

