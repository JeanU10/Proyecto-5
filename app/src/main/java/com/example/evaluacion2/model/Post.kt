package com.example.evaluacion2.model

data class Post(
    val id: String,
    val autor: String,
    val autorImagen: String,
    val tiempo: String,
    val tipo: TipoPost,
    val contenido: String,
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
