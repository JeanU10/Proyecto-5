package com.example.evaluacion2.controller

import com.example.evaluacion2.model.User

class UserController {

    private val usuarios = mutableListOf<User>()

    fun registrarUsuario(user: User): Boolean {
        if (usuarios.any { it.correo == user.correo || it.usuario == user.usuario }) {
            return false
        }
        usuarios.add(user)
        return true
    }

    fun obtenerUsuarios(): List<User> = usuarios
}