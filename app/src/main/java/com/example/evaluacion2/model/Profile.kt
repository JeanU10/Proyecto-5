package com.example.evaluacion2.model

data class Profile(
    val nombre: String,
    val rol: String,
    val ubicacion: String,
    val imagenUrl: String,
    val comunidadesCount: Int,
    val puntuacion: Double,
    val impactoMensual: String,
    val interaccionesUltimos30Dias: Int,
    val comunidadesUsuario: List<ComunidadUsuario>,
    val actividadReciente: List<ActividadReciente>
)

data class ComunidadUsuario(
    val nombre: String,
    val imagenUrl: String
)

data class ActividadReciente(
    val descripcion: String,
    val tiempo: String
)