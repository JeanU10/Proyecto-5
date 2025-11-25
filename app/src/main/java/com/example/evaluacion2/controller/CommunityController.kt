package com.example.evaluacion2.controller

import com.example.evaluacion2.model.Community
import com.example.evaluacion2.model.EstadoComunidad
import com.example.evaluacion2.model.RolUsuario

class CommunityController {

    private val comunidades = mutableListOf<Community>()

    init {
        cargarComunidadesMock()
    }

    private fun cargarComunidadesMock() {
        comunidades.addAll(listOf(
            Community(
                id = "1",
                nombre = "Huerto Urbano Centro",
                descripcion = "Huerto comunitario en el centro",
                imagenUrl = "",
                miembros = 1200,
                activosRecientes = 2,
                distancia = "2 km",
                estado = EstadoComunidad.ABIERTA,
                categoria = "Destacadas"
            ),
            Community(
                id = "2",
                nombre = "Asamblea Barrio Norte",
                descripcion = "Asamblea vecinal",
                imagenUrl = "",
                miembros = 540,
                activosRecientes = 5,
                distancia = "5 km",
                estado = EstadoComunidad.SOLICITAR_ACCESO,
                categoria = "Cerca"
            ),
            Community(
                id = "3",
                nombre = "Cooperativa MakerLab",
                descripcion = "Espacio maker comunitario",
                imagenUrl = "",
                miembros = 320,
                activosRecientes = 11,
                distancia = "11 km",
                estado = EstadoComunidad.AUTOGESTION,
                categoria = "Destacadas"
            ),
            Community(
                id = "4",
                nombre = "Círculo de Energía Local",
                descripcion = "Energías renovables",
                imagenUrl = "",
                miembros = 210,
                activosRecientes = 0,
                distancia = "",
                estado = EstadoComunidad.ABIERTA,
                rol = RolUsuario.MODERADOR,
                categoria = "Mis comunidades"
            ),
            Community(
                id = "5",
                nombre = "Mercado Solidario",
                descripcion = "Comercio justo y local",
                imagenUrl = "",
                miembros = 980,
                activosRecientes = 0,
                distancia = "",
                estado = EstadoComunidad.ABIERTA,
                rol = RolUsuario.MIEMBRO,
                categoria = "Mis comunidades"
            ),
            Community(
                id = "6",
                nombre = "Cooperativa Barrio Sur",
                descripcion = "Cooperativa de consumo",
                imagenUrl = "",
                miembros = 32,
                activosRecientes = 5,
                distancia = "",
                estado = EstadoComunidad.ABIERTA,
                categoria = "Nuevas"
            )
        ))
    }

    fun obtenerComunidades(): List<Community> = comunidades

    fun filtrarPorCategoria(categoria: String): List<Community> {
        return comunidades.filter { it.categoria == categoria }
    }

    fun buscarComunidades(query: String): List<Community> {
        return comunidades.filter {
            it.nombre.contains(query, ignoreCase = true) ||
                    it.descripcion.contains(query, ignoreCase = true)
        }
    }

    fun unirseAComunidad(comunidadId: String): Boolean {
        val comunidad = comunidades.find { it.id == comunidadId }
        return comunidad != null
    }
}