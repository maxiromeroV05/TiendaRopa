// Paquete donde se encuentra la pantalla del catálogo
package com.example.vest0.viewmodel

// Importaciones necesarias para la interfaz de usuario y manejo de listas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vest0.data.Catalogo.camisas
import com.example.vest0.data.Catalogo.chaquetas
import com.example.vest0.data.Catalogo.jeans
import com.example.vest0.data.Catalogo.pantalones
import com.example.vest0.data.Catalogo.polerasHombre
import com.example.vest0.data.Catalogo.polerasMujer
import com.example.vest0.data.Catalogo.polerones
import com.example.vest0.data.Catalogo.vestidos


import com.example.vest0.model.Producto
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.ceil


// Pantalla principal del catálogo de productos
@Composable
fun CatalogoScreen(onProductoClick: (Producto) -> Unit) {
    var busqueda by remember { mutableStateOf("") } // Estado para el texto de búsqueda

    // Lista de secciones con sus productos correspondientes
    val secciones: List<Pair<String, List<Producto>>> = listOf(
        "Poleras Hombre" to polerasHombre,
        "Poleras Mujer" to polerasMujer,
        "Polerones" to polerones,
        "Pantalones" to pantalones,
        "Jeans" to jeans,
        "Chaquetas" to chaquetas,
        "Vestidos" to vestidos,
        "Camisas" to camisas
    )

    // Filtra los productos según el texto de búsqueda
    val seccionesFiltradas = secciones.map { (titulo, productos) ->
        val filtrados = productos.filter {
            it.nombre.contains(busqueda, ignoreCase = true) ||
                    it.descripcion.contains(busqueda, ignoreCase = true)
        }
        titulo to filtrados
    }.filter { it.second.isNotEmpty() } // Solo secciones con productos filtrados

    // Lista vertical que contiene el buscador y las secciones filtradas
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // Campo de búsqueda
        item {
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

        // Itera sobre cada sección filtrada
        seccionesFiltradas.forEach { (titulo, productos) ->
            // Título de la sección
            item {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp)
                )
            }

            // Grid de productos
            item {
                val alturaFijaPorTarjeta = 220.dp // Altura de cada tarjeta
                val espaciadoVertical = 16.dp // Espacio entre filas
                val numeroDeFilas = ceil(productos.size / 2.0).toInt() // Calcula número de filas
                val alturaTotalDelGrid = (alturaFijaPorTarjeta * numeroDeFilas) +
                        (espaciadoVertical * (numeroDeFilas - 1)) // Altura total del grid

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Dos columnas
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(alturaTotalDelGrid)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(espaciadoVertical),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    userScrollEnabled = false // Desactiva scroll interno
                ) {
                    // Tarjetas de productos
                    items(productos) { producto ->
                        ProductoCard(
                            producto = producto,
                            modifier = Modifier.height(alturaFijaPorTarjeta),
                            onClick = { onProductoClick(producto) }
                        )
                    }
                }
            }
        }
    }
}

// Composable que representa una tarjeta de producto
@Composable
fun ProductoCard(producto: Producto, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val format = remember {
        NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply {
            maximumFractionDigits = 0 // Sin decimales
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }, // Acción al hacer clic
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // ✅ Imagen del producto
            Image(
                painter = painterResource(id = producto.imagenRes),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            // Información textual del producto
            Column {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    maxLines = 2
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Precio del producto
            Text(
                text = format.format(producto.precio),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

// Pantalla de detalle de producto
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoDetalleScreen(producto: Producto, onBack: () -> Unit) {
    val format = remember {
        NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply {
            maximumFractionDigits = 0
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = producto.nombre) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // ✅ Imagen ampliada del producto
            Image(
                painter = painterResource(id = producto.imagenRes),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = producto.descripcion,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Precio: ${format.format(producto.precio)}",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}