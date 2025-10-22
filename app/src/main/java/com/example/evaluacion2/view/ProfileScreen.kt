package com.example.evaluacion2.view

import androidx.compose.foundation.background
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
import com.example.evaluacion2.controller.AuthController
import com.example.evaluacion2.model.*
import com.example.evaluacion2.navigation.Screen
import com.example.evaluacion2.view.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    // Obtener el usuario actual
    val currentUser = AuthController.getCurrentUser()
    val isGuest = AuthController.isGuest()

    // Crear datos del perfil basados en el usuario actual
    val nombreCompleto = if (isGuest) {
        "Invitado"
    } else {
        "${currentUser?.nombre ?: ""} ${currentUser?.apellido ?: ""}".trim()
    }

    val nombreUsuario = if (isGuest) {
        "@invitado"
    } else {
        "@${currentUser?.usuario ?: "usuario"}"
    }

    val correoUsuario = if (isGuest) {
        "Sin correo"
    } else {
        currentUser?.correo ?: ""
    }

    val profile = remember(currentUser, isGuest) {
        Profile(
            nombre = nombreCompleto,
            rol = if (isGuest) "Visitante" else "Miembro",
            ubicacion = correoUsuario,
            imagenUrl = "",
            comunidadesCount = if (isGuest) 0 else 6,
            puntuacion = if (isGuest) 0.0 else 4.7,
            impactoMensual = if (isGuest) "0%" else "+12%",
            interaccionesUltimos30Dias = if (isGuest) 0 else 348,
            comunidadesUsuario = if (isGuest) emptyList() else listOf(
                ComunidadUsuario("Huertas Urbanas", ""),
                ComunidadUsuario("Barrio Sur", "")
            ),
            actividadReciente = if (isGuest) emptyList() else listOf(
                ActividadReciente("Comentario en \"Huertas Urbanas\"", "hace 3h"),
                ActividadReciente("Votó en encuesta \"Reciclaje 2025\"", "ayer"),
                ActividadReciente("Publicó en \"Barrio Sur\"", "hace 3d")
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController, currentRoute = "profile")
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0E0E0))
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                                .padding(16.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            profile.nombre,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            nombreUsuario,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            "${profile.rol} · ${profile.ubicacion}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )

                        if (isGuest) {
                            Spacer(modifier = Modifier.height(16.dp))

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFFFF3E0)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    "Regístrate para acceder a todas las funciones",
                                    modifier = Modifier.padding(12.dp),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFE65100)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Card(
                                modifier = Modifier.weight(1f),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("Comunidades", style = MaterialTheme.typography.bodySmall)
                                    Text(
                                        profile.comunidadesCount.toString(),
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Card(
                                modifier = Modifier.weight(1f),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("Puntuación", style = MaterialTheme.typography.bodySmall)
                                    Text(
                                        profile.puntuacion.toString(),
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (isGuest) {
                                Button(
                                    onClick = {
                                        navController.navigate(Screen.Register.route)
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Registrarse")
                                }
                            } else {
                                OutlinedButton(
                                    onClick = { },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Editar perfil")
                                }

                                Button(
                                    onClick = { },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Compartir")
                                }
                            }
                        }
                    }
                }
            }

            if (!isGuest) {
                item {
                    Text(
                        "Resumen",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Card(
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Impacto mensual", style = MaterialTheme.typography.bodySmall)
                                Text(
                                    profile.impactoMensual,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "vs. mes anterior",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }

                        Card(
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Interacciones", style = MaterialTheme.typography.bodySmall)
                                Text(
                                    profile.interaccionesUltimos30Dias.toString(),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "últimos 30 días",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }

                item {
                    Text(
                        "Tus comunidades",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                items(profile.comunidadesUsuario) { comunidad ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
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
                                Text(comunidad.nombre, fontWeight = FontWeight.Bold)
                            }
                            Text("Ver", color = Color.Gray)
                        }
                    }
                }

                item {
                    Text(
                        "Actividad reciente",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                items(profile.actividadReciente) { actividad ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
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
                            Text(actividad.descripcion, modifier = Modifier.weight(1f))
                            Text(actividad.tiempo, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        AuthController.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Cerrar sesión", modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}
