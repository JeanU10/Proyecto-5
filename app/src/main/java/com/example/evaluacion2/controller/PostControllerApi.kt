package com.example.evaluacion2.controller

import android.util.Log
import com.example.evaluacion2.model.Post
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Response

// 1. Definimos la interfaz aquí mismo (o puedes dejarla en ApiService.kt si prefieres)
interface PostApiService {
    @GET("/api/posts")
    suspend fun obtenerPosts(): Response<List<Post>>

    @POST("/api/posts")
    suspend fun crearPost(@Body post: Post): Response<Post>
}

/**
 * PostController
 *
 * Este objeto es el "cerebro" encargado de manejar los Posts.
 * Sigue el patrón Singleton (object) para que exista una sola conexión
 * activa en toda la app, ahorrando recursos.
 */
object PostControllerApi {

    // IMPORTANTE: Cambia esto por la URL que te de Vercel al final.
    // Si usas emulador de Android Studio y pruebas un servidor local en tu PC, usa "http://10.0.2.2:3000/"
    private const val BASE_URL = "http://10.0.2.2:3000/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(PostApiService::class.java)

    // Función para subir post
    suspend fun crearPost(post: Post, onResult: (Boolean) -> Unit) {
        try {
            val response = apiService.crearPost(post)
            if (response.isSuccessful) {
                Log.d("API", "Post subido con ID: ${response.body()?.id}")
                onResult(true)
            } else {
                Log.e("API", "Error servidor: ${response.code()}")
                onResult(false)
            }
        } catch (e: Exception) {
            Log.e("API", "Error conexión: ${e.message}")
            onResult(false)
        }
    }

    // Función para traer el feed
    suspend fun obtenerPosts(onResult: (List<Post>) -> Unit) {
        try {
            val response = apiService.obtenerPosts()
            if (response.isSuccessful) {
                val lista = response.body() ?: emptyList()
                onResult(lista)
            } else {
                onResult(emptyList())
            }
        } catch (e: Exception) {
            Log.e("API", "Error trayendo posts: ${e.message}")
            onResult(emptyList())
        }
    }
}