package com.example.evaluacion2.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.evaluacion2.controller.AuthController
import com.example.evaluacion2.controller.PostRepository
import com.example.evaluacion2.model.Post
import com.example.evaluacion2.model.TipoPost
import com.example.evaluacion2.navigation.Screen
import com.example.evaluacion2.view.components.BottomNavBar
import java.io.File
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val currentUser = AuthController.getCurrentUser()
    val nombreUsuario = if (AuthController.isGuest()) {
        "Invitado"
    } else {
        "${currentUser?.nombre ?: ""} ${currentUser?.apellido ?: ""}".trim().ifEmpty { "Usuario" }
    }

    var titulo by remember { mutableStateOf("") }
    var contenido by remember { mutableStateOf("") }
    var comentariosActivados by remember { mutableStateOf(true) }
    var visibilidadSoloMiembros by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showGuestDialog by remember { mutableStateOf(false) }
    var showImageOptionsDialog by remember { mutableStateOf(false) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageUriString by remember { mutableStateOf("") }

    val tempPhotoUri = remember {
        val photoFile = File(context.cacheDir, "temp_photo_${System.currentTimeMillis()}.jpg")
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            photoFile
        )
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imageUri = tempPhotoUri
            imageUriString = tempPhotoUri.toString()
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
            imageUriString = it.toString()
        }
    }

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

                        Surface(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                nombreUsuario,
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

                    if (imageUriString.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(imageUriString),
                                contentDescription = "Imagen seleccionada",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            IconButton(
                                onClick = {
                                    imageUri = null
                                    imageUriString = ""
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Eliminar imagen",
                                    tint = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .border(
                                width = 2.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .clickable {
                                if (AuthController.isGuest()) {
                                    showGuestDialog = true
                                } else {
                                    showImageOptionsDialog = true
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                if (imageUriString.isEmpty()) "Añadir imagen o video" else "Cambiar imagen",
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            if (imageUriString.isEmpty()) {
                                Text(
                                    "Toca para seleccionar",
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
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
                        imageUri = null
                        imageUriString = ""
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

                            val imagenesUrl = if (imageUriString.isNotEmpty()) {
                                listOf(imageUriString)
                            } else {
                                emptyList()
                            }

                            val nuevoPost = Post(
                                id = UUID.randomUUID().toString(),
                                autor = nombreUsuario,
                                autorImagen = currentUser?.imagenUrl ?: "",
                                tiempo = "Ahora",
                                tipo = TipoPost.PUBLICACION,
                                contenido = contenidoFinal,
                                imagenesUrl = imagenesUrl,
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

    if (showImageOptionsDialog) {
        AlertDialog(
            onDismissRequest = { showImageOptionsDialog = false },
            title = {
                Text("Agregar imagen")
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showImageOptionsDialog = false
                                cameraLauncher.launch(tempPhotoUri)
                            },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Cámara",
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text("Tomar foto", fontWeight = FontWeight.Bold)
                                Text("Usar la cámara", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showImageOptionsDialog = false
                                galleryLauncher.launch("image/*")
                            },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoLibrary,
                                contentDescription = "Galería",
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text("Subir foto", fontWeight = FontWeight.Bold)
                                Text("Desde galería", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showImageOptionsDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

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
                        imageUri = null
                        imageUriString = ""
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
