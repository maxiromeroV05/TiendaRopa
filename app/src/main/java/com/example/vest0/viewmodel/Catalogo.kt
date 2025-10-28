package com.example.vest0.data

import com.example.vest0.R
import com.example.vest0.model.Producto


object Catalogo {

    val polerasHombre = listOf(
        Producto("Polera Básica Blanca", "Algodón orgánico, cuello redondo.", 14990.0, "Blanco", "M", "Hombre",R.drawable.ic_polera_basica_playstore),
        Producto("Polera Negra Slim", "Corte ajustado con estilo moderno.", 15990.0, "Negro", "M", "Hombre", R.drawable.ic_polera_slim_playstore),
        Producto("Polera Verde Oliva", "Tono tierra, suave al tacto.", 16990.0, "Verde Oliva", "L", "Hombre", R.drawable.ic_polera_verde_oliva_playstore),
        Producto("Polera Azul Marino", "Estilo casual con textura ligera.", 16990.0, "Azul Marino", "M", "Hombre", R.drawable.ic_polera_azul_marino_playstore),
        Producto("Polera Beige Premium", "Corte clásico, tela de alta calidad.", 17990.0, "Beige", "M", "Hombre", R.drawable.ic_polera_beige_playstore),
        Producto("Polera Estampada Minimal", "Diseño gráfico discreto al frente.", 17990.0, "Blanco", "L", "Hombre", R.drawable.ic_polera_miniminal_playstore)
    )

    val polerasMujer = listOf(
        Producto("Polera Cropped Blanca", "Corte corto, algodón suave.", 15990.0, "Blanco", "S", "Mujer", R.drawable.ic_polera_cropp_blanca_playstore),
        Producto("Polera Rosa Palo", "Tono suave, ideal para primavera.", 16990.0, "Rosa Palo", "M", "Mujer", R.drawable.ic_polera_rosa_playstore),
        Producto("Polera Negra con Volantes", "Detalles femeninos y elegantes.", 18990.0, "Negro", "M", "Mujer", R.drawable.ic_polera_volante_playstore),
        Producto("Polera de Lino Natural", "Fresca, cuello redondo.", 17990.0, "Beige", "S", "Mujer", R.drawable.ic_polera_lino_playstore),
        Producto("Polera Azul Cielo", "Color claro, estilo veraniego.", 16990.0, "Azul Cielo", "M", "Mujer", R.drawable.ic_polera_azul_cielo_playstore),
        Producto("Polera Oversize Gris", "Ajuste relajado, tendencia moderna.", 15990.0, "Gris", "L", "Mujer", R.drawable.ic_polera_oversize_playstore)
    )

    val polerones = listOf(
        Producto("Polerón Capucha Basic", "Canguro frontal, interior suave.", 25990.0, "Gris", "M", "Hombre", R.drawable.ic_poleron_capucha_playstore),
        Producto("Polerón Blanco Premium", "Diseño limpio, corte moderno.", 27990.0, "Blanco", "L", "Hombre", R.drawable.ic_poleron_blanco_playstore),
        Producto("Polerón Negro Oversize", "Corte amplio urbano.", 26990.0, "Negro", "L", "Hombre", R.drawable.ic_poleron_negro_oversize_playstore),
        Producto("Polerón Verde Menta", "Color tendencia 2025.", 28990.0, "Verde Menta", "S", "Mujer", R.drawable.ic_poleron_verde_menta_playstore),
        Producto("Polerón Marrón Tierra", "Textura suave tipo fleece.", 27990.0, "Marrón", "M", "Mujer", R.drawable.ic_poleron_marron_playstore)
    )

    val pantalones = listOf(
        Producto("Pantalón Cargo Slim", "Bolsillos laterales, estilo moderno.", 29990.0, "Caqui", "M", "Hombre", R.drawable.ic_pantalones_cargo_playstore),
        Producto("Pantalón Chino Beige", "Corte elegante con pinzas.", 28990.0, "Beige", "L", "Hombre", R.drawable.ic_pantalones_chino_b_playstore),
        Producto("Pantalón de Lino", "Ligero y fresco para verano.", 31990.0, "Arena", "M", "Hombre", R.drawable.ic_pantalones_lino_playstore),
        Producto("Pantalón Recto Mujer", "Corte clásico con caída suave.", 31990.0, "Negro", "S", "Mujer", R.drawable.ic_pantalon_recto_playstore),
        Producto("Pantalón Wide Leg", "Pierna ancha moderna.", 32990.0, "Beige", "M", "Mujer", R.drawable.ic_pantalon_wide_playstore),
        Producto("Pantalón Formal Negro", "Ideal para oficina o eventos.", 33990.0, "Negro", "M", "Mujer", R.drawable.ic_pantalon_formal_m_playstore)
    )

    val jeans = listOf(
        Producto("Jean Slim Fit Azul", "Denim elástico, corte moderno.", 29990.0, "Azul Medio", "M", "Hombre", R.drawable.ic_jeans_slim_fit_playstore),
        Producto("Jean Negro Skinny", "Corte ajustado contemporáneo.", 28990.0, "Negro", "L", "Hombre", R.drawable.ic_jeans_skinny_n_playstore),
        Producto("Jean Regular Fit", "Corte clásico, denim suave.", 28990.0, "Azul Oscuro", "M", "Hombre", R.drawable.ic_jeans_regular_fit_playstore),
        Producto("Jean Mom Fit", "Estilo noventero con cintura alta.", 29990.0, "Azul Claro", "S", "Mujer", R.drawable.ic_jeans_mom_fit_playstore),
        Producto("Jean Wide Leg Mujer", "Corte ancho y cómodo.", 31990.0, "Azul Medio", "M", "Mujer", R.drawable.ic_jeans_wide_leg_playstore),
        Producto("Jean Blanco Mujer", "Perfecto para temporada primavera.", 30990.0, "Blanco", "S", "Mujer", R.drawable.ic_polera_basica_playstore)
    )

    val chaquetas = listOf(
        Producto("Chaqueta Cuero Sintético", "Estilo biker clásico.", 45990.0, "Negro", "L", "Hombre", R.drawable.ic_chaqueta_cuero_sintetico_playstore),
        Producto("Chaqueta Denim", "Clásico de mezclilla.", 37990.0, "Azul", "M", "Hombre", R.drawable.ic_chaqueta_denim_playstore),
        Producto("Chaqueta Bomber", "Diseño urbano y moderno.", 39990.0, "Verde Oliva", "M", "Hombre", R.drawable.ic_chaqueta_bomber_playstore),
        Producto("Chaqueta Trench Mujer", "Gabardina ligera con cinturón.", 42990.0, "Beige", "M", "Mujer", R.drawable.ic_chaqueta_trench_mujer_playstore),
        Producto("Chaqueta de Cuero Mujer", "Corte ajustado elegante.", 45990.0, "Negro", "S", "Mujer", R.drawable.ic_chaqueta_cuero_mujer_playstore),
        Producto("Chaqueta Puffer", "Acolchada, ideal invierno.", 44990.0, "Gris", "M", "Unisex", R.drawable.ic_chaqueta_puffer_playstore)
    )

    val vestidos = listOf(
        Producto("Vestido Largo Satén", "Tela fluida y elegante.", 37990.0, "Negro", "M", "Mujer", R.drawable.ic_vestido_largo_saten_playstore),
        Producto("Vestido Corto Floral", "Estampado primaveral.", 32990.0, "Verde", "S", "Mujer", R.drawable.ic_vestido_corto_floral_playstore),
        Producto("Vestido Midi Beige", "Corte clásico con caída suave.", 34990.0, "Beige", "M", "Mujer", R.drawable.ic_vestido_midi_beige_playstore),
        Producto("Vestido Negro Cruzado", "Diseño envolvente y formal.", 35990.0, "Negro", "L", "Mujer", R.drawable.ic_vestido_negro_cruzadoplaystore),
        Producto("Vestido Blanco Verano", "Alg.",precio = 34.990, color = "Blanco", talla = "M", genero = "Mujer",R.drawable.ic_vestido_blanco_verano_playstore),
    )
        // --- Sección: Camisas ---
    val camisas = listOf(
        Producto("Camisa Blanca Clásica", "Corte regular, 100% algodón.", 24990.0, "Blanco", "M", "Hombre", R.drawable.ic_camisa_blanca_playstore),
        Producto("Camisa Celeste", "Formal y versátil.", 25990.0, "Celeste", "M", "Hombre", R.drawable.ic_camisa_celeste_playstore),
        Producto("Camisa Lino Natural", "Fresca y ligera.", 26990.0, "Beige", "L", "Hombre", R.drawable.ic_camisa_lino_natural_playstore),
        Producto("Camisa Satinada Mujer", "Tacto suave y elegante.", 28990.0, "Champagne", "S", "Mujer", R.drawable.ic_camisa_satinada_mujer_playstore),
        Producto("Camisa Negra Ajustada", "Corte moderno femenino.", 26990.0, "Negro", "M", "Mujer", R.drawable.ic_camisa_negra_ajustada_playstore),
        Producto("Camisa de Cuadros", "Casual y combinable.", 24990.0, "Rojo y Negro", "L", "Hombre", R.drawable.ic_camisa_cuadro_playstore)

    )

        val todosLosProductos : List<Producto> = polerasHombre + polerasMujer + polerones + pantalones + jeans + chaquetas + vestidos + camisas
}