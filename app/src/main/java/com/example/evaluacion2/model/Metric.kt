package com.example.evaluacion2.model

data class Metric(
    val comunidadNombre: String,
    val comunidadDescripcion: String,
    val categoria: String,
    val icono: IconoMetrica
)

enum class IconoMetrica {
    TRANSPORTE,
    HUERTAS,
    TEXTIL
}

data class MetricaDetallada(
    val periodo: PeriodoTiempo,
    val miembrosActivos: Int,
    val cambioMiembros: Int,
    val nuevosIngresos: Int,
    val cambioIngresos: Int,
    val participacion: Int,
    val cambioParticipacion: Int,
    val encuestasActivas: Int,
    val cambioEncuestas: Int,
    val topComunidades: List<TopComunidad>,
    val objetivos: List<Objetivo>
)

enum class PeriodoTiempo {
    SIETE_DIAS,
    TREINTA_DIAS,
    NOVENTA_DIAS
}

data class TopComunidad(
    val nombre: String,
    val imagenUrl: String,
    val interacciones: String,
    val cambio: String
)

data class Objetivo(
    val titulo: String,
    val valor: String,
    val estado: EstadoObjetivo,
    val periodo: String
)

enum class EstadoObjetivo {
    EN_CURSO,
    MEJORANDO,
    COMPLETADO
}