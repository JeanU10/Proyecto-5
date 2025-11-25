package com.example.evaluacion2.controller

import com.example.evaluacion2.model.Post

object PostRepository {
    private val postController = PostController()

    fun obtenerPosts(): List<Post> = postController.obtenerPosts()

    fun crearPost(post: Post): Boolean = postController.crearPost(post)
}
