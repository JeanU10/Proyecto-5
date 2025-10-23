package com.example.evaluacion2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.evaluacion2.controller.AuthController
import com.example.evaluacion2.controller.PostRepository
import com.example.evaluacion2.model.Post
import com.example.evaluacion2.model.TipoPost
import com.example.evaluacion2.navigation.Screen
import com.example.evaluacion2.view.components.BottomNavBar
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var posts by remember { mutableStateOf(PostRepository.obtenerPosts()) }

    LaunchedEffect(Unit) {
        posts = PostRepository.obtenerPosts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inicio", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController, currentRoute = "home")
        }
    ) { paddingValues ->
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

            item {
                CreatePostCard(
                    navController = navController,
                    onPostCreated = { newPost ->
                        PostRepository.crearPost(newPost)
                        posts = PostRepository.obtenerPosts()
                    }
                )
            }

            item {
                Text(
                    "Actividad reciente",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold
                )
            }

            items(posts) { post ->
                PostCard(post = post)
            }
        }
    }
}

@Composable
fun CreatePostCard(navController: NavController, onPostCreated: (Post) -> Unit) {
    var postText by remember { mutableStateOf("") }
    var showGuestDialog by remember { mutableStateOf(false) }
    val currentUser = AuthController.getCurrentUser()
    val nombreUsuario = if (AuthController.isGuest()) {
        "Invitado"
    } else {
        "${currentUser?.nombre ?: ""} ${currentUser?.apellido ?: ""}".trim().ifEmpty { "Usuario" }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (currentUser?.imagenUrl?.isNotEmpty() == true) {
                    Image(
                        painter = rememberAsyncImagePainter(currentUser.imagenUrl),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))
                Text(nombreUsuario, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Text("Cambiar", color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = postText,
                onValueChange = { postText = it },
                placeholder = { Text("Comparte algo con tu comunidad...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                enabled = !AuthController.isGuest()
            )

            Spacer(modifier = Modifier.height(12.dp))
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
                    Text("Imagen", color = Color.Black)
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
                    Text("Encuesta", color = Color.Black)
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
                    Text("Evento", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        postText = ""
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Borrador")
                }

                Button(
                    onClick = {
                        if (AuthController.isGuest()) {
                            showGuestDialog = true
                        } else if (postText.isNotBlank()) {
                            val nuevoPost = Post(
                                id = UUID.randomUUID().toString(),
                                autor = nombreUsuario,
                                autorImagen = currentUser?.imagenUrl ?: "",
                                tiempo = "Ahora",
                                tipo = TipoPost.PUBLICACION,
                                contenido = postText,
                                imagenesUrl = emptyList(),
                                opcionesEncuesta = emptyList(),
                                comunidad = "Cooperativa Barrio Sur",
                                comentariosCount = 0
                            )

                            onPostCreated(nuevoPost)
                            postText = ""
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(8.dp),
                    enabled = postText.isNotBlank() || AuthController.isGuest()
                ) {
                    Text("Publicar")
                }
            }
        }
    }

    if (showGuestDialog) {
        AlertDialog(
            onDismissRequest = { showGuestDialog = false },
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
                    onClick = { showGuestDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun PostCard(post: Post) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (post.autorImagen.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(post.autorImagen),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(post.autor, fontWeight = FontWeight.Bold)
                    Text(
                        "${post.tiempo} • ${post.tipo.name}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                if (post.tipo == TipoPost.ENCUESTA) {
                    Text(
                        "${post.opcionesEncuesta.sumOf { it.porcentaje }}%",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                } else {
                    Text(
                        "${post.comentariosCount}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(post.contenido)

            if (post.tipo == TipoPost.PUBLICACION && post.imagenesUrl.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(post.imagenesUrl.take(4)) { imageUrl ->
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = "Imagen del post",
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            if (post.tipo == TipoPost.ENCUESTA) {
                Spacer(modifier = Modifier.height(12.dp))
                post.opcionesEncuesta.forEach { opcion ->
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(opcion.texto)
                    }
                }
            }
        }
    }
}
