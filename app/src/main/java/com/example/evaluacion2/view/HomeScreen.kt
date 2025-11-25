package com.example.evaluacion2.view

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage // Importante para las imágenes
import coil.compose.rememberAsyncImagePainter
import com.example.evaluacion2.controller.AuthController
import com.example.evaluacion2.controller.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.evaluacion2.model.Post
import com.example.evaluacion2.model.TipoPost
import com.example.evaluacion2.navigation.Screen
import com.example.evaluacion2.view.components.BottomNavBar
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    val posts by viewModel.posts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inicio", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController, currentRoute = "home")
        }
    ) { paddingValues ->

        if (isLoading && posts.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE0E0E0))
                    .padding(paddingValues)
            ) {
                item {
                    Text(
                        "Bienvenida de vuelta. Aquí están las novedades de tus comunidades.",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Sección para crear post rápido
                item {
                    CreatePostCard(
                        navController = navController,
                        onPostCreated = { newPost ->
                            viewModel.subirPost(newPost)
                        }
                    )
                }

                if(isLoading && posts.isNotEmpty()) {
                    item { LinearProgressIndicator(modifier = Modifier.fillMaxWidth()) }
                }

                item {
                    Text(
                        "Actividad reciente",
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold
                    )
                }

                // Lista de posts traídos de Azure
                items(posts) { post ->
                    PostCard(post = post)
                }
            }
        }
    }
}

@Composable
fun CreatePostCard(navController: NavController, onPostCreated: (Post) -> Unit) {
    var postText by remember { mutableStateOf("") }
    var showGuestDialog by remember { mutableStateOf(false) }

    // --- SOLUCIÓN AL ERROR DE nombreUsuario ---
    // Recuperamos el usuario actual aquí mismo para poder usar sus datos
    val currentUser = AuthController.getCurrentUser()
    val nombreUsuario = if (AuthController.isGuest()) {
        "Invitado"
    } else {
        // Usamos el nombre del usuario o "Usuario" si está vacío
        "${currentUser?.nombre ?: ""} ${currentUser?.apellido ?: ""}".trim().ifEmpty { "Usuario" }
    }
    // ------------------------------------------

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Foto de perfil del usuario actual
                if (currentUser?.imagenUrl?.isNotEmpty() == true) {
                    AsyncImage(
                        model = currentUser.imagenUrl,
                        contentDescription = "Mi Perfil",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Campo de texto simple
                OutlinedTextField(
                    value = postText,
                    onValueChange = { postText = it },
                    placeholder = { Text("¿Qué estás pensando?") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón de publicar
            Button(
                onClick = {
                    if (AuthController.isGuest()) {
                        showGuestDialog = true
                    } else if (postText.isNotBlank()) {
                        // Creamos el post con los datos corregidos
                        val nuevoPost = Post(
                            id = null, // Azure generará el ID
                            autor = nombreUsuario, // ¡Ahora sí existe!
                            autorImagen = currentUser?.imagenUrl ?: "",
                            tiempo = "Ahora",
                            tipo = TipoPost.PUBLICACION,
                            contenido = postText,
                            comunidad = "General",
                            comentariosCount = 0
                        )
                        onPostCreated(nuevoPost)
                        postText = "" // Limpiar campo
                    }
                },
                modifier = Modifier.align(Alignment.End),
                enabled = postText.isNotBlank()
            ) {
                Text("Publicar")
            }
        }
    }

    // Diálogo para invitados (si aplica)
    if (showGuestDialog) {
        AlertDialog(
            onDismissRequest = { showGuestDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showGuestDialog = false
                    navController.navigate(Screen.Register.route)
                }) { Text("Registrarse") }
            },
            dismissButton = {
                TextButton(onClick = { showGuestDialog = false }) { Text("Cancelar") }
            },
            title = { Text("Acción restringida") },
            text = { Text("Debes registrarte para publicar.") }
        )
    }
}

// --- AQUÍ ESTÁ EL CÓDIGO DE POSTCARD QUE FALTABA ---
@Composable
fun PostCard(post: Post) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // 1. Encabezado (Foto Autor, Nombre, Fecha)
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Usamos AsyncImage para cargar la URL de Azure
                AsyncImage(
                    model = post.autorImagen,
                    contentDescription = "Avatar de ${post.autor}",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop,
                    // Si falla la carga, muestra un icono por defecto
                    error = rememberAsyncImagePainter(model = com.example.evaluacion2.R.drawable.ic_launcher_foreground)
                    // Nota: Asegúrate de tener un drawable o quita la linea 'error' si no tienes uno a mano
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = post.autor,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = post.tiempo,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Etiqueta del tipo (opcional)
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = post.tipo.name,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2. Contenido de texto
            Text(
                text = post.contenido,
                style = MaterialTheme.typography.bodyMedium
            )

            // 3. Imagen del Post (Si existe en la lista de URLs)
            if (!post.imagenesUrl.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                AsyncImage(
                    model = post.imagenesUrl.first(),
                    contentDescription = "Imagen del post",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4. Pie de tarjeta (Comunidad)
            Divider(color = Color.LightGray.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Comunidad: ${post.comunidad}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}