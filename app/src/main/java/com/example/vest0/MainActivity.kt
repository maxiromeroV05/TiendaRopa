package com.example.vest0

import UbicacionScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.vest0.datastore.UserPreferences
import com.example.vest0.model.Producto
import com.example.vest0.model.Usuario
import com.example.vest0.repository.UsuarioRepositorySQLite
import com.example.vest0.viewmodel.*

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
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val emailGuardado by prefs.getEmail.collectAsState(initial = null)

    val navController = rememberNavController()
    val usuarioRegistrado = remember { mutableStateOf<Usuario?>(null) }
    val usuarioLogueado = remember { mutableStateOf(false) }
    val productosMap = remember { mutableStateMapOf<String, Producto>() }

    LaunchedEffect(emailGuardado) {
        if (emailGuardado != null) {
            val repo = UsuarioRepositorySQLite(context)
            val usuario = repo.obtenerPorEmail(emailGuardado!!)
            if (usuario != null) {
                usuarioRegistrado.value = usuario
                usuarioLogueado.value = true
                navController.navigate("perfil") {
                    popUpTo("menu") { inclusive = false }
                }
            }
        }
    }

    Scaffold(
        topBar = { VestoTopBar() },
        bottomBar = {
            BottomNavigationBar { route -> navController.navigate(route) }
        }
    ) { innerPadding ->
        AppNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            usuarioRegistrado = usuarioRegistrado,
            usuarioLogueado = usuarioLogueado,
            productosMap = productosMap
        )
    }
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    usuarioRegistrado: MutableState<Usuario?>,
    usuarioLogueado: MutableState<Boolean>,
    productosMap: SnapshotStateMap<String, Producto>
) {
    NavHost(
        navController = navController,
        startDestination = "menu",
        modifier = modifier
    ) {
        composable("menu") { CentralContent() }

        composable("ropa") {
            CatalogoScreen { producto ->
                val id = producto.nombre
                productosMap[id] = producto
                navController.navigate("detalle/$id")
            }
        }

        composable("detalle/{productoId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("productoId")
            val producto = productosMap[id]
            producto?.let {
                ProductoDetalleScreen(producto = it) {
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
                onIrRegistro = { navController.navigate("registro") },
                onCerrarSesion = {
                    usuarioLogueado.value = false
                    usuarioRegistrado.value = null
                    navController.navigate("menu") { popUpTo("menu") { inclusive = true } }
                },
                onEliminarUsuario = {
                    usuarioLogueado.value = false
                    usuarioRegistrado.value = null
                    navController.navigate("menu") { popUpTo("menu") { inclusive = true } }
                }
            )
        }

        composable("registro") {
            RegistroScreen(
                onRegistroExitoso = { usuario ->
                    usuarioRegistrado.value = usuario
                    usuarioLogueado.value = true
                    navController.navigate("perfil") {
                        popUpTo("menu") { inclusive = false }
                    }
                }
            )
        }

        composable("login") {
            LoginScreen(
                usuarioRegistrado = usuarioRegistrado.value,
                onLoginExitoso = { usuario ->
                    usuarioRegistrado.value = usuario
                    usuarioLogueado.value = true
                    navController.navigate("perfil") {
                        popUpTo("menu") { inclusive = false }
                    }
                },
                onIrRegistro = { navController.navigate("registro") }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VestoTopBar() {
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
}

@Composable
fun CentralContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.ic_foto_menu_playstore),
            contentDescription = "Imagen del menú principal",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun BottomNavigationBar(onNavigate: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarButton("Ropa", R.drawable.ic_ropa_playstore) { onNavigate("ropa") }
        BottomBarButton("Ubicación", R.drawable.ic_location_playstore) { onNavigate("ubicacion") }
        BottomBarButton("Perfil", R.drawable.ic_profile_playstore) { onNavigate("perfil") }
        BottomBarButton("Inicio", R.drawable.ic_home_playstore) { onNavigate("menu") }
    }
}

@Composable
fun RowScope.BottomBarButton(text: String, iconRes: Int, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .padding(vertical = 4.dp),
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