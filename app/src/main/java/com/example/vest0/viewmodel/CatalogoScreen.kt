package com.example.vest0.viewmodel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vest0.data.Catalogo
import com.example.vest0.model.Producto
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.ceil

@Composable
fun CatalogoScreen(onProductoClick: (Producto) -> Unit) {
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

    val seccionesFiltradas = secciones.map { (titulo, productos) ->
        val filtrados = productos.filter {
            it.nombre.contains(busqueda, ignoreCase = true) ||
                    it.descripcion.contains(busqueda, ignoreCase = true)
        }
        titulo to filtrados
    }.filter { it.second.isNotEmpty() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
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

        seccionesFiltradas.forEach { (titulo, productos) ->
            item {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp)
                )
            }
            item {
                val alturaFijaPorTarjeta = 220.dp
                val espaciadoVertical = 16.dp
                val numeroDeFilas = ceil(productos.size / 2.0).toInt()
                val alturaTotalDelGrid = (alturaFijaPorTarjeta * numeroDeFilas) +
                        (espaciadoVertical * (numeroDeFilas - 1))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(alturaTotalDelGrid)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(espaciadoVertical),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    userScrollEnabled = false
                ) {
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

@Composable
fun ProductoCard(producto: Producto, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val format = remember {
        NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply {
            maximumFractionDigits = 0
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
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
            Text(
                text = format.format(producto.precio),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

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
                            imageVector = Icons.Filled.ArrowBack,
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