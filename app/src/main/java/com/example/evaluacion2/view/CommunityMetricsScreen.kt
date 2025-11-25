package com.example.evaluacion2.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.evaluacion2.controller.MetricsController
import com.example.evaluacion2.model.EstadoObjetivo
import com.example.evaluacion2.model.PeriodoTiempo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityMetricsScreen(navController: NavController, communityName: String) {
    val metricsController = remember { MetricsController() }
    var selectedPeriod by remember { mutableStateOf(PeriodoTiempo.SIETE_DIAS) }
    val metricaDetallada = remember(selectedPeriod) {
        metricsController.obtenerMetricaDetallada(communityName, selectedPeriod)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Métricas comunitarias", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0E0E0))
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PeriodButton(
                        text = "7d",
                        selected = selectedPeriod == PeriodoTiempo.SIETE_DIAS,
                        onClick = { selectedPeriod = PeriodoTiempo.SIETE_DIAS }
                    )
                    PeriodButton(
                        text = "30d",
                        selected = selectedPeriod == PeriodoTiempo.TREINTA_DIAS,
                        onClick = { selectedPeriod = PeriodoTiempo.TREINTA_DIAS }
                    )
                    PeriodButton(
                        text = "90d",
                        selected = selectedPeriod == PeriodoTiempo.NOVENTA_DIAS,
                        onClick = { selectedPeriod = PeriodoTiempo.NOVENTA_DIAS }
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricSmallCard(
                        title = "Miembros activos",
                        value = metricaDetallada.miembrosActivos.toString(),
                        change = "+${metricaDetallada.cambioMiembros}%",
                        isPositive = true,
                        modifier = Modifier.weight(1f)
                    )
                    MetricSmallCard(
                        title = "Nuevos ingresos",
                        value = metricaDetallada.nuevosIngresos.toString(),
                        change = "+${metricaDetallada.cambioIngresos}",
                        isPositive = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricSmallCard(
                        title = "Participación",
                        value = "${metricaDetallada.participacion}%",
                        change = "${metricaDetallada.cambioParticipacion}%",
                        isPositive = false,
                        modifier = Modifier.weight(1f)
                    )
                    MetricSmallCard(
                        title = "Encuestas activas",
                        value = metricaDetallada.encuestasActivas.toString(),
                        change = "+${metricaDetallada.cambioEncuestas}",
                        isPositive = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Actividad semanal", fontWeight = FontWeight.Bold)

                        Row(
                            modifier = Modifier.padding(top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(Color.Black, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Publicaciones", style = MaterialTheme.typography.bodySmall)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(Color.Gray, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Comentarios", style = MaterialTheme.typography.bodySmall)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(top = 16.dp)
                                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("[Gráfico de actividad]", color = Color.Gray)
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Ver detalle")
                            }
                            Button(
                                onClick = { },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Exportar")
                            }
                        }
                    }
                }
            }

            item {
                Text("Top comunidades por interacción", fontWeight = FontWeight.Bold)
            }

            items(metricaDetallada.topComunidades) { comunidad ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                                .padding(8.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(comunidad.nombre, fontWeight = FontWeight.Bold)
                            Text(
                                comunidad.interacciones,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                        Text(comunidad.cambio, color = if (comunidad.cambio.startsWith("+")) Color(0xFF4CAF50) else Color.Red)
                    }
                }
            }

            item {
                Text(
                    "Objetivos de la comunidad",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    "Q3 2025",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            items(metricaDetallada.objetivos) { objetivo ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(objetivo.titulo)
                            Text(
                                objetivo.valor,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Surface(
                            color = when (objetivo.estado) {
                                EstadoObjetivo.EN_CURSO -> Color(0xFF4CAF50)
                                EstadoObjetivo.MEJORANDO -> Color(0xFF4CAF50)
                                EstadoObjetivo.COMPLETADO -> Color(0xFF2196F3)
                            },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                when (objetivo.estado) {
                                    EstadoObjetivo.EN_CURSO -> "En curso"
                                    EstadoObjetivo.MEJORANDO -> "Mejorando"
                                    EstadoObjetivo.COMPLETADO -> "Completado"
                                },
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Conecta una fuente de datos para ver métricas avanzadas y tableros personalizados.",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun PeriodButton(text: String, selected: Boolean, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (selected) Color.White else Color.Transparent
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.width(80.dp)
    ) {
        Text(text)
    }
}

@Composable
fun MetricSmallCard(
    title: String,
    value: String,
    change: String,
    isPositive: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Text(
                value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            LinearProgressIndicator(
                progress = 0.5f,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                color = if (isPositive) Color(0xFF4CAF50) else Color.Red
            )
            Text(
                change,
                style = MaterialTheme.typography.labelSmall,
                color = if (isPositive) Color(0xFF4CAF50) else Color.Red
            )
        }
    }
}