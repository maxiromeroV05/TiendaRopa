package com.example.vest0.viewmodel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vest0.data.Catalogo
// CORRECCIÓN IMPORTANTE: Asegúrate de que este import sea el correcto.
import com.example.vest0.model.Producto
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.ceil

@Composable
fun CatalogoScreen() {
    var busqueda by remember { mutableStateOf("") }

    val secciones = listOf(
        "Poleras Hombre" to Catalogo.polerasHombre,
        "Poleras Mujer" to Catalogo.polerasMujer,
        "Polerones" to Catalogo.polerones,
        "Pantalones" to Catalogo.pantalones,
        "Jeans" to Catalogo.jeans,
        "Chaquetas" to Catalogo.chaquetas,
        "Vestidos" to Catalogo.vestidos,
        "Camisas" to Catalogo.camisas
    )

    // Tu lógica de filtrado por sección está muy bien, la mantenemos.
    val seccionesFiltradas = secciones.map { (titulo, productos) ->
        val filtrados = if (busqueda.isBlank()) productos else productos.filter {
            it.nombre.contains(busqueda, ignoreCase = true) ||
                    it.descripcion.contains(busqueda, ignoreCase = true)
        }
        titulo to filtrados
    }.filter { it.second.isNotEmpty() } // Solo mostramos secciones que tienen productos después de filtrar.

    // El LazyColumn será el único componente con scroll principal.
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            // Barra de búsqueda funcional
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                placeholder = { Text("¿QUÉ ESTÁS BUSCANDO?", fontSize = 14.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        // Recorremos las secciones filtradas para mostrarlas.
        seccionesFiltradas.forEach { (titulo, productos) ->
            // Mostramos el título de la sección
            item {
                Text(
                    text = titulo,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp)
                )
            }

            // Aquí está la solución al problema del scroll anidado
            item {
                val alturaFijaPorTarjeta = 220.dp // Altura fija para cada tarjeta de producto.
                val espaciadoVertical = 16.dp
                val numeroDeFilas = ceil(productos.size / 2.0).toInt() // Calculamos cuántas filas necesitamos.
                val alturaTotalDelGrid = (alturaFijaPorTarjeta * numeroDeFilas) + (espaciadoVertical * (numeroDeFilas - 1))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(alturaTotalDelGrid) // Le damos una altura calculada y fija al grid.
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(espaciadoVertical),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    userScrollEnabled = false // El scroll lo maneja el LazyColumn padre.
                ) {
                    items(productos) { producto ->
                        ProductoCard(producto = producto, modifier = Modifier.height(alturaFijaPorTarjeta))
                    }
                }
            }
        }
    }
}

@Composable
fun ProductoCard(producto: Producto, modifier: Modifier = Modifier) {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    format.maximumFractionDigits = 0

    Card(
        modifier = modifier.fillMaxWidth(), // Usamos el modifier que pasamos como parámetro.
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxHeight(), // Rellena la altura de la tarjeta.
            verticalArrangement = Arrangement.SpaceBetween // Empuja el precio hacia abajo.
        ) {
            Column {
                Text(
                    text = producto.nombre,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = producto.descripcion,
                    fontSize = 12.sp,
                    maxLines = 3,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = format.format(producto.precio),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

