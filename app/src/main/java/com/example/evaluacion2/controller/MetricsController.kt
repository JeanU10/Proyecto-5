package com.example.evaluacion2.controller

import com.example.evaluacion2.model.*

class MetricsController {

    private val metricas = mutableListOf<Metric>()

    init {
        cargarMetricasMock()
    }

    private fun cargarMetricasMock() {
        metricas.addAll(listOf(
            Metric(
                comunidadNombre = "Transporte Local",
                comunidadDescripcion = "Optimización de rutas, feedback de paradas y horarios.",
                categoria = "Movilidad",
                icono = IconoMetrica.TRANSPORTE
            ),
            Metric(
                comunidadNombre = "Huertas Urbanas",
                comunidadDescripcion = "Intercambio de semillas, voluntariados y métricas de cosecha.",
                categoria = "Sustentable",
                icono = IconoMetrica.HUERTAS
            ),
            Metric(
                comunidadNombre = "Cooperativa Textil",
                comunidadDescripcion = "Producción, pedidos y métricas de productividad.",
                categoria = "Trabajo",
                icono = IconoMetrica.TEXTIL
            )
        ))
    }

    fun obtenerMetricas(): List<Metric> = metricas

    fun obtenerMetricaDetallada(nombreComunidad: String, periodo: PeriodoTiempo): MetricaDetallada {
        return MetricaDetallada(
            periodo = periodo,
            miembrosActivos = 128,
            cambioMiembros = 12,
            nuevosIngresos = 24,
            cambioIngresos = 5,
            participacion = 63,
            cambioParticipacion = -3,
            encuestasActivas = 4,
            cambioEncuestas = 1,
            topComunidades = listOf(
                TopComunidad("Huertas Urbanas", "", "Interacciones 1.2k", "+8%"),
                TopComunidad("Reciclaje Local", "", "Interacciones 980", "+3%"),
                TopComunidad("Cooperativa Barrio Sur", "", "Interacciones 730", "-2%")
            ),
            objetivos = listOf(
                Objetivo("Tasa de respuesta en encuestas", "78%", EstadoObjetivo.EN_CURSO, "Q3 2025"),
                Objetivo("Tiempo medio de resolución", "2.4 d", EstadoObjetivo.MEJORANDO, "")
            )
        )
    }
}