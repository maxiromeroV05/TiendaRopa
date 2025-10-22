package com.example.vest0

// 📦 Android y sistema
import UbicacionScreen
import android.os.Bundle

// 📦 Jetpack Compose
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// 📦 Navegación
import androidx.navigation.compose.*
import com.example.vest0.model.Producto
import com.example.vest0.model.Usuario
import com.example.vest0.viewmodel.CatalogoScreen
// Importación añadida para CatalogoScreen
import com.example.vest0.viewmodel.LoginScreen
import com.example.vest0.viewmodel.PerfilScreen
import com.example.vest0.viewmodel.ProductoDetalleScreen
import com.example.vest0.viewmodel.RegistroScreen
// Nota: Se asume que las vistas (Screens) están en un paquete 'view'

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VestoApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VestoApp() {
    val navController = rememberNavController()
    val usuarioRegistrado = remember { mutableStateOf<Usuario?>(null) }
    val usuarioLogueado = remember { mutableStateOf(false) }
    // Usar un mapa mutable para pasar datos complejos entre pantallas es una solución válida.
    val productosMap = remember { mutableStateMapOf<String, Producto>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "VESTO",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        },
        bottomBar = {
            // Se pasa el NavController directamente para una navegación más limpia
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "menu",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("menu") { CentralContent() }

            // En MainActivity.kt
            composable("ropa") {
                CatalogoScreen { producto -> // <<-- CatalogoScreen ahora acepta esta lambda
                    // Esta lógica ahora se ejecuta correctamente porque CatalogoScreen
                    // la invoca al hacer clic en un producto.
                    val id = producto.nombre
                    productosMap[id] = producto
                    navController.navigate("detalle/$id")
                }
            }


            composable("detalle/{productoId}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("productoId")
                // Se recupera el producto del mapa.
                val producto = productosMap[id]
                producto?.let {
                    ProductoDetalleScreen(producto = it) {
                        // Acción para volver atrás en la pila de navegación.
                        navController.popBackStack()
                    }
                }
            }
            composable("ubicacion") { UbicacionScreen() }
            composable("perfil") {
                PerfilScreen(
                    usuario = usuarioRegistrado.value,
                    logeado = usuarioLogueado.value,
                    onIrLogin = { navController.navigate("login") },
                    onIrRegistro = { navController.navigate("registro") }
                )
            }
            composable("registro") {
                RegistroScreen { usuario ->
                    usuarioRegistrado.value = usuario
                    navController.navigate("perfil")
                }
            }
            composable("login") {
                LoginScreen(
                    usuarioRegistrado = usuarioRegistrado.value,
                    onLoginExitoso = {
                        usuarioLogueado.value = true
                        navController.navigate("perfil")
                    },
                    onIrRegistro = { navController.navigate("registro") }
                )
            }
        }
    }
}

// CORRECCIÓN: El tipo genérico "ERROR" fue reemplazado por "Producto".
// Además, se movió la implementación fuera para mayor claridad, asumiendo que esta
// es una pantalla compleja que debería estar en su propio fichero.
/*
@Composable
fun CatalogoScreen(onProductoClick: (Producto) -> Unit) {
    // Aquí iría la implementación real de tu catálogo, por ejemplo:
    // val viewModel: ProductoViewModel = viewModel()
    // val productos = viewModel.productos.collectAsState().value
    // LazyColumn(modifier = Modifier.fillMaxSize()) {
    //     items(productos) { producto ->
    //         Text(
    //             text = producto.nombre,
    //             modifier = Modifier.clickable { onProductoClick(producto) }
    //         )
    //     }
    // }
    Text("Implementación de CatalogoScreen pendiente.")
}
*/
// Se deja la implementación de las otras vistas fuera de MainActivity por claridad.

@Composable
fun CentralContent() {
    Image(
        painter = painterResource(id = R.drawable.ic_foto_menu_playstore),
        contentDescription = "Imagen del menú principal",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

// MODIFICACIÓN: Se pasa el NavController para manejar la navegación.
@Composable
fun BottomNavigationBar(navController: androidx.navigation.NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarButton("ropa", R.drawable.ic_ropa_playstore, Modifier.weight(1f)) {
            navController.navigate("ropa")
        }
        BottomBarButton("ubicacion", R.drawable.ic_location_playstore, Modifier.weight(1f)) {
            navController.navigate("ubicacion")
        }
        BottomBarButton("perfil", R.drawable.ic_profile_playstore, Modifier.weight(1f)) {
            navController.navigate("perfil")
        }
        BottomBarButton("menu", R.drawable.ic_home_playstore, Modifier.weight(1f)) {
            navController.navigate("menu")
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
