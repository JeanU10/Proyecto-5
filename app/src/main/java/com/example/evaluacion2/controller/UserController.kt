package com.example.evaluacion2.controller

import com.example.evaluacion2.model.User

object UserController {
    private val usuarios = mutableListOf<User>()

    fun registrarUsuario(user: User): Boolean {
        if (usuarios.any { it.correo == user.correo || it.usuario == user.usuario }) {
            return false
        }
        usuarios.add(user)
        return true
    }

    fun actualizarUsuario(updatedUser: User) {
        val index = usuarios.indexOfFirst { it.correo == updatedUser.correo }
        if (index != -1) {
            usuarios[index] = updatedUser
        }
    }

    fun obtenerUsuarios(): List<User> = usuarios
}
