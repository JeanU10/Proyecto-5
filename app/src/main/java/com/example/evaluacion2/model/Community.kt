package com.example.evaluacion2.model

data class Community(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val imagenUrl: String,
    val miembros: Int,
    val activosRecientes: Int,
    val distancia: String,
    val estado: EstadoComunidad,
    val rol: RolUsuario? = null,
    val categoria: String
)

enum class EstadoComunidad {
    ABIERTA,
    AUTOGESTION,
    SOLICITAR_ACCESO
}

enum class RolUsuario {
    MODERADOR,
    MIEMBRO,
    COORDINADOR
}
