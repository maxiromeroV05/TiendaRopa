// Ruta del fichero: app/src/main/java/com/example/vest0/viewmodel/CatalogoScreen.kt

package com.example.vest0.viewmodel // Asegúrate que el package sea el correcto

// 📦 Imports necesarios
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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

// --- INICIO DE LA CORRECCIÓN ---

// 1. CAMBIO FUNDAMENTAL: Se modifica la firma de la función.
// Ahora acepta una lambda `onProductoClick` para comunicar eventos hacia el exterior.
// Ya no tiene su propio NavController ni NavHost.
@Composable
fun CatalogoScreen(onProductoClick: (Producto) -> Unit) { // <<-- CAMBIO CLAVE
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
        val filtrados = if (busqueda.isBlank()) productos else productos.filter {
            it.nombre.contains(busqueda, ignoreCase = true) || it.descripcion.contains(busqueda, ignoreCase = true)
        }
        titulo to filtrados
    }.filter { it.second.isNotEmpty() }

    // El LazyColumn que contiene toda la pantalla.
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color.White),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                placeholder = { Text("¿QUÉ ESTÁS BUSCANDO?", fontSize = 14.sp) },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
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
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp)
                )
            }
            item {
                // Tu lógica para calcular la altura del grid es ingeniosa, la mantenemos.
                val alturaFijaPorTarjeta = 220.dp
                val espaciadoVertical = 16.dp
                val numeroDeFilas = ceil(productos.size / 2.0).toInt()
                val alturaTotalDelGrid = (alturaFijaPorTarjeta * numeroDeFilas) + (espaciadoVertical * (numeroDeFilas - 1))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth().height(alturaTotalDelGrid).padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(espaciadoVertical),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    userScrollEnabled = false
                ) {
                    items(productos) { producto ->
                        ProductoCard(producto = producto, modifier = Modifier.height(alturaFijaPorTarjeta)) {
                            // 2. MEJORA: En lugar de navegar aquí...
                            // ...simplemente notificamos hacia afuera usando la lambda.
                            onProductoClick(producto) // <<-- CAMBIO CLAVE
                        }
                    }
                }
            }
        }
    }
}

// --- FIN DE LA CORRECCIÓN ---

// La función ProductoCard no necesita cambios. Es reutilizable y está bien diseñada.
@Composable
fun ProductoCard(producto: Producto, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    format.maximumFractionDigits = 0
    Card(
        modifier = modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = producto.nombre, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 2)
                Spacer(Modifier.height(4.dp))
                Text(text = producto.descripcion, fontSize = 12.sp, maxLines = 3, color = MaterialTheme.colorScheme.onSurfaceVariant)
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

// 3. RECOMENDACIÓN: Mueve ProductoDetalleScreen a su propio fichero.
// Por ahora, puedes dejarlo aquí o moverlo para mejor organización.
// Su lógica interna no necesita cambios.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoDetalleScreen(producto: Producto, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = producto.nombre) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
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
                .padding(16.dp)
        ) {
            Text(
                text = producto.descripcion,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Precio: ${NumberFormat.getCurrencyInstance(Locale("es", "CL")).format(producto.precio)}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
