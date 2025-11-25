package com.example.evaluacion2.controller

import com.example.evaluacion2.model.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * ApiService
 *
 * Aquí definimos los "endpoints" o rutas de nuestra API en Vercel.
 * Retrofit usará esto para saber a dónde pedir datos.
 */
interface ApiService {

    // OBTENER: Pide la lista de posts al servidor
    // La respuesta será una lista de objetos 'Post'
    @GET("/api/posts")
    suspend fun obtenerPosts(): Response<List<Post>>

    // CREAR: Envía un nuevo post al servidor
    // Enviamos un objeto 'Post' en el cuerpo (@Body) de la petición
    @POST("/api/posts")
    suspend fun crearPost(@Body post: Post): Response<Post>
}