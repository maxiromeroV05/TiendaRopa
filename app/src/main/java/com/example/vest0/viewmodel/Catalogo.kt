package com.example.vest0.data

import com.example.vest0.model.Producto

object Catalogo {
        // --- Sección: Poleras Hombre ---
        val polerasHombre = listOf(
            Producto("Polera Básica Blanca", "Algodón orgánico, cuello redondo.", 14990.0, "Blanco", "M", "Hombre"),
            Producto("Polera Negra Slim", "Corte ajustado con estilo moderno.", 15990.0, "Negro", "M", "Hombre"),
            Producto("Polera Verde Oliva", "Tono tierra, suave al tacto.", 16990.0, "Verde Oliva", "L", "Hombre"),
            Producto("Polera Azul Marino", "Estilo casual con textura ligera.", 16990.0, "Azul Marino", "M", "Hombre"),
            Producto("Polera Beige Premium", "Corte clásico, tela de alta calidad.", 17990.0, "Beige", "M", "Hombre"),
            Producto("Polera Estampada Minimal", "Diseño gráfico discreto al frente.", 17990.0, "Blanco", "L", "Hombre")
        )

        // --- Sección: Poleras Mujer ---
        val polerasMujer = listOf(
            Producto("Polera Cropped Blanca", "Corte corto, algodón suave.", 15990.0, "Blanco", "S", "Mujer"),
            Producto("Polera Rosa Palo", "Tono suave, ideal para primavera.", 16990.0, "Rosa Palo", "M", "Mujer"),
            Producto("Polera Negra con Volantes", "Detalles femeninos y elegantes.", 18990.0, "Negro", "M", "Mujer"),
            Producto("Polera de Lino Natural", "Fresca, cuello redondo.", 17990.0, "Beige", "S", "Mujer"),
            Producto("Polera Azul Cielo", "Color claro, estilo veraniego.", 16990.0, "Azul Cielo", "M", "Mujer"),
            Producto("Polera Oversize Gris", "Ajuste relajado, tendencia moderna.", 15990.0, "Gris", "L", "Mujer")
        )

        // --- Sección: Polerones ---
        val polerones = listOf(
            Producto("Polerón Capucha Basic", "Canguro frontal, interior suave.", 25990.0, "Gris", "M", "Hombre"),
            Producto("Polerón Blanco Premium", "Diseño limpio, corte moderno.", 27990.0, "Blanco", "L", "Hombre"),
            Producto("Polerón Negro Oversize", "Corte amplio urbano.", 26990.0, "Negro", "L", "Hombre"),
            Producto("Polerón Beige Suave", "Minimalista y cálido.", 28990.0, "Beige", "M", "Mujer"),
            Producto("Polerón Verde Menta", "Color tendencia 2025.", 28990.0, "Verde Menta", "S", "Mujer"),
            Producto("Polerón Marrón Tierra", "Textura suave tipo fleece.", 27990.0, "Marrón", "M", "Mujer")
        )

        // --- Sección: Pantalones ---
        val pantalones = listOf(
            Producto("Pantalón Cargo Slim", "Bolsillos laterales, estilo moderno.", 29990.0, "Caqui", "M", "Hombre"),
            Producto("Pantalón Chino Beige", "Corte elegante con pinzas.", 28990.0, "Beige", "L", "Hombre"),
            Producto("Pantalón de Lino", "Ligero y fresco para verano.", 31990.0, "Arena", "M", "Hombre"),
            Producto("Pantalón Recto Mujer", "Corte clásico con caída suave.", 31990.0, "Negro", "S", "Mujer"),
            Producto("Pantalón Wide Leg", "Pierna ancha moderna.", 32990.0, "Beige", "M", "Mujer"),
            Producto("Pantalón Formal Negro", "Ideal para oficina o eventos.", 33990.0, "Negro", "M", "Mujer")
        )

        // --- Sección: Jeans ---
        val jeans = listOf(
            Producto("Jean Slim Fit Azul", "Denim elástico, corte moderno.", 29990.0, "Azul Medio", "M", "Hombre"),
            Producto("Jean Negro Skinny", "Corte ajustado contemporáneo.", 28990.0, "Negro", "L", "Hombre"),
            Producto("Jean Regular Fit", "Corte clásico, denim suave.", 28990.0, "Azul Oscuro", "M", "Hombre"),
            Producto("Jean Mom Fit", "Estilo noventero con cintura alta.", 29990.0, "Azul Claro", "S", "Mujer"),
            Producto("Jean Wide Leg Mujer", "Corte ancho y cómodo.", 31990.0, "Azul Medio", "M", "Mujer"),
            Producto("Jean Blanco Mujer", "Perfecto para temporada primavera.", 30990.0, "Blanco", "S", "Mujer")
        )

        // --- Sección: Chaquetas ---
        val chaquetas = listOf(
            Producto("Chaqueta Cuero Sintético", "Estilo biker clásico.", 45990.0, "Negro", "L", "Hombre"),
            Producto("Chaqueta Denim", "Clásico de mezclilla.", 37990.0, "Azul", "M", "Hombre"),
            Producto("Chaqueta Bomber", "Diseño urbano y moderno.", 39990.0, "Verde Oliva", "M", "Hombre"),
            Producto("Chaqueta Trench Mujer", "Gabardina ligera con cinturón.", 42990.0, "Beige", "M", "Mujer"),
            Producto("Chaqueta de Cuero Mujer", "Corte ajustado elegante.", 45990.0, "Negro", "S", "Mujer"),
            Producto("Chaqueta Puffer", "Acolchada, ideal invierno.", 44990.0, "Gris", "M", "Unisex")
        )

        // --- Sección: Vestidos ---
        val vestidos = listOf(
            Producto("Vestido Largo Satén", "Tela fluida y elegante.", 37990.0, "Negro", "M", "Mujer"),
            Producto("Vestido Corto Floral", "Estampado primaveral.", 32990.0, "Verde", "S", "Mujer"),
            Producto("Vestido Midi Beige", "Corte clásico con caída suave.", 34990.0, "Beige", "M", "Mujer"),
            Producto("Vestido Negro Cruzado", "Diseño envolvente y formal.", 35990.0, "Negro", "L", "Mujer"),
            Producto("Vestido Blanco Verano", "Algodón ligero con tirantes.", 31990.0, "Blanco", "M", "Mujer"),
            Producto("Vestido Rojo Satinado", "Ideal para eventos o cenas.", 38990.0, "Rojo", "S", "Mujer")
        )

        // --- Sección: Camisas ---
        val camisas = listOf(
            Producto("Camisa Blanca Clásica", "Corte regular, 100% algodón.", 24990.0, "Blanco", "M", "Hombre"),
            Producto("Camisa Celeste", "Formal y versátil.", 25990.0, "Celeste", "M", "Hombre"),
            Producto("Camisa Lino Natural", "Fresca y ligera.", 26990.0, "Beige", "L", "Hombre"),
            Producto("Camisa Satinada Mujer", "Tacto suave y elegante.", 28990.0, "Champagne", "S", "Mujer"),
            Producto("Camisa Negra Ajustada", "Corte moderno femenino.", 26990.0, "Negro", "M", "Mujer"),
            Producto("Camisa de Cuadros", "Casual y combinable.", 24990.0, "Rojo y Negro", "L", "Hombre")
        )
        val todosLosProductos: List<Producto> = polerasHombre + polerasMujer + polerones + pantalones + jeans + chaquetas + vestidos + camisas
}