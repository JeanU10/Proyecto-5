package com.example.evaluacion2.controller

import com.example.evaluacion2.model.User

object AuthController {
    private var currentUser: User? = null
    private var isGuestMode: Boolean = false

    fun login(correo: String, contrasena: String, usuarios: List<User>): Boolean {
        val usuario = usuarios.find { it.correo == correo && it.contrasena == contrasena }
        return if (usuario != null) {
            currentUser = usuario
            isGuestMode = false
            true
        } else {
            false
        }
    }

    fun register(user: User): Boolean {
        currentUser = user
        isGuestMode = false
        return true
    }

    fun updateUser(updatedUser: User) {
        currentUser = updatedUser
        UserController.actualizarUsuario(updatedUser)
    }

    fun loginAsGuest() {
        isGuestMode = true
        currentUser = null
    }

    fun logout() {
        currentUser = null
        isGuestMode = false
    }

    fun getCurrentUser(): User? = currentUser

    fun isLoggedIn(): Boolean = currentUser != null || isGuestMode

    fun isGuest(): Boolean = isGuestMode
}
