package com.example.evaluacion2.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.evaluacion2.controller.PostRepository
import com.example.evaluacion2.model.Post
import com.example.evaluacion2.model.TipoPost
import com.example.evaluacion2.navigation.Screen
import com.example.evaluacion2.view.components.BottomNavBar
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(navController: NavController) {
    var titulo by remember { mutableStateOf("") }
    var contenido by remember { mutableStateOf("") }
    var comentariosActivados by remember { mutableStateOf(true) }
    var visibilidadSoloMiembros by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showGuestDialog by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    // Verificar si es invitado al intentar escribir
    LaunchedEffect(Unit) {
        if (AuthController.isGuest()) {
            showGuestDialog = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva publicación", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController, currentRoute = "create_post")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0E0E0))
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                "Comparte una actualización con tu comunidad. Puedes añadir imagen y enlaces.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                                .padding(8.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Surface(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                "Cooperativa Barrio Sur",
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Surface(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                "Público",
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = titulo,
                        onValueChange = {
                            if (AuthController.isGuest()) {
                                showGuestDialog = true
                            } else {
                                titulo = it
                            }
                        },
                        placeholder = { Text("Título (opcional)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        enabled = !AuthController.isGuest()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = contenido,
                        onValueChange = {
                            if (AuthController.isGuest()) {
                                showGuestDialog = true
                            } else {
                                contenido = it
                            }
                        },
                        placeholder = { Text("¿Qué está pasando?") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        shape = RoundedCornerShape(8.dp),
                        enabled = !AuthController.isGuest()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .border(
                                width = 2.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(Color.White, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Añadir imagen o video",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                if (AuthController.isGuest()) {
                                    showGuestDialog = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Enlace", color = Color.Black)
                        }

                        Button(
                            onClick = {
                                if (AuthController.isGuest()) {
                                    showGuestDialog = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Etiquetas", color = Color.Black)
                        }

                        Button(
                            onClick = {
                                if (AuthController.isGuest()) {
                                    showGuestDialog = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Programar", color = Color.Black)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Opciones",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Comentarios", fontWeight = FontWeight.Bold)
                            Text(
                                "Permitir respuestas y reacciones",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }

                        Surface(
                            color = if (comentariosActivados) Color(0xFF4CAF50) else Color.LightGray,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                if (comentariosActivados) "Activados" else "Desactivados",
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                color = if (comentariosActivados) Color.White else Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Visibilidad", fontWeight = FontWeight.Bold)
                            Text(
                                "Quién puede ver esta publicación",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }

                        Surface(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                if (visibilidadSoloMiembros) "Solo miembros" else "Público",
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        titulo = ""
                        contenido = ""
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Guardar borrador")
                }

                Button(
                    onClick = {
                        if (AuthController.isGuest()) {
                            showGuestDialog = true
                        } else if (contenido.isNotBlank()) {
                            val contenidoFinal = if (titulo.isNotBlank()) {
                                "$titulo\n\n$contenido"
                            } else {
                                contenido
                            }

                            val nuevoPost = Post(
                                id = UUID.randomUUID().toString(),
                                autor = "Usuario",
                                autorImagen = "",
                                tiempo = "Ahora",
                                tipo = TipoPost.PUBLICACION,
                                contenido = contenidoFinal,
                                imagenesUrl = emptyList(),
                                opcionesEncuesta = emptyList(),
                                comunidad = "Cooperativa Barrio Sur",
                                comentariosCount = 0
                            )

                            PostRepository.crearPost(nuevoPost)
                            showSuccessDialog = true
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(8.dp),
                    enabled = contenido.isNotBlank() || AuthController.isGuest()
                ) {
                    Text("Publicar")
                }
            }
        }
    }

    // Diálogo de éxito
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text("¡Publicación creada!")
            },
            text = {
                Text("Tu publicación se ha compartido con la comunidad.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        titulo = ""
                        contenido = ""
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                ) {
                    Text("Ver en Home")
                }
            }
        )
    }

    // Diálogo para usuarios invitados
    if (showGuestDialog) {
        AlertDialog(
            onDismissRequest = {
                showGuestDialog = false
                navController.popBackStack()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            },
            title = {
                Text("Registro requerido")
            },
            text = {
                Text("Para publicar contenido necesitas crear una cuenta. ¿Deseas registrarte ahora?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showGuestDialog = false
                        navController.navigate(Screen.Register.route)
                    }
                ) {
                    Text("Registrarse")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showGuestDialog = false
                        navController.popBackStack()
                    }
                ) {
                    Text("Volver")
                }
            }
        )
    }
}
