package com.example.evaluacion2.controller

import com.example.evaluacion2.model.Post
import com.example.evaluacion2.model.TipoPost
import com.example.evaluacion2.model.OpcionEncuesta

class PostController {
    private val posts = mutableListOf<Post>()

    init {
        cargarPostsMock()
    }

    private fun cargarPostsMock() {
        posts.addAll(listOf(
            Post(
                id = "1",
                autor = "María",
                autorImagen = "",
                tiempo = "hace 2 h",
                tipo = TipoPost.PUBLICACION,
                contenido = "Reunión del sábado para organizar la feria comunitaria. ¡Participa!",
                imagenesUrl = listOf("", "", "", ""),
                comunidad = "Cooperativa Barrio Sur",
                comentariosCount = 24
            ),
            Post(
                id = "2",
                autor = "Raúl",
                autorImagen = "",
                tiempo = "hace 5 h",
                tipo = TipoPost.ENCUESTA,
                contenido = "¿Qué día prefieres para el taller de compostaje?",
                opcionesEncuesta = listOf(
                    OpcionEncuesta("Sábado", 42, false),
                    OpcionEncuesta("Domingo", 58, false)
                ),
                comunidad = "Huerto Urbano Centro",
                comentariosCount = 0
            )
        ))
    }

    fun obtenerPosts(): List<Post> = posts.toList()

    fun crearPost(post: Post): Boolean {
        posts.add(0, post) // Agregar al inicio de la lista
        return true
    }

    fun votarEncuesta(postId: String, opcionIndex: Int): Boolean {
        val post = posts.find { it.id == postId }
        return post != null && post.tipo == TipoPost.ENCUESTA
    }
}
