package com.example.evaluacion2.model

data class User(
    val nombre: String,
    val apellido: String,
    val correo: String,
    val usuario: String,
    val contrasena: String,
    val recibirNovedades: Boolean,
    val imagenUrl: String = ""
)
