package com.example.evaluacion2.model

data class Post(
    // 1. El ID ahora es String? (puede ser nulo) y por defecto es null.
    // Esto sirve para crear posts nuevos que aún no han ido al servidor.
    val id: String? = null,

    val autor: String,
    val autorImagen: String,
    val tiempo: String,
    val tipo: TipoPost,
    val contenido: String,

    // Listas vacías por defecto para evitar errores de nulos
    val imagenesUrl: List<String> = emptyList(),
    val opcionesEncuesta: List<OpcionEncuesta> = emptyList(),

    val comunidad: String,
    val comentariosCount: Int = 0
)

enum class TipoPost {
    PUBLICACION,
    ENCUESTA,
    EVENTO
}

data class OpcionEncuesta(
    val texto: String,
    val porcentaje: Int,
    val seleccionada: Boolean = false
)