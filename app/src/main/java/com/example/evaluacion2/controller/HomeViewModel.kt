package com.example.evaluacion2.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.evaluacion2.controller.PostControllerApi

class HomeViewModel : ViewModel() {

    // _posts es privado y mutable (nosotros lo modificamos aquí)
    private val _posts = MutableStateFlow<List<Post>>(emptyList())

    // posts es público e inmutable (la vista solo lo "observa", no lo toca)
    val posts: StateFlow<List<Post>> = _posts

    // Estado para saber si está cargando (para mostrar una ruedita de carga)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        // Apenas se crea este ViewModel, cargamos los datos
        cargarPosts()
    }

    fun cargarPosts() {
        viewModelScope.launch {
            _isLoading.value = true // Empieza a cargar

            // Llamamos a tu controlador que habla con Azure
            PostControllerApi.obtenerPosts { listaRecibida ->
                _posts.value = listaRecibida
                _isLoading.value = false // Terminó de cargar
            }
        }
    }

    fun subirPost(post: Post) {
        viewModelScope.launch {
            _isLoading.value = true
            // 1. Subimos el post a Vercel/Azure
            PostControllerApi.crearPost(post) { exito ->
                // 2. Si subió bien, recargamos la lista completa para ver el nuevo post
                if (exito) {
                    cargarPosts()
                } else {
                    _isLoading.value = false // Si falló, solo quitamos la carga
                }
            }
        }
    }
}