/*package com.example.evaluacion2.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.* // Asegúrate de usar Material3
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage // Requiere la librería Coil
import com.example.evaluacion2.model.Post

@Composable
fun PostCard(post: Post) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // --- Encabezado: Foto Autor + Nombre + Tiempo ---
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Imagen del autor (Carga desde URL)
                AsyncImage(
                    model = post.autorImagen, // URL de Azure
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(text = post.autor, fontWeight = FontWeight.Bold)
                    Text(text = post.tiempo, fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                }

                Spacer(modifier = Modifier.weight(1f))

                // Etiqueta del tipo de post (Publicación/Encuesta)
                AssistChip(
                    onClick = { },
                    label = { Text(post.tipo.name) } // Muestra "PUBLICACION"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // --- Contenido del Post ---
            Text(text = post.contenido, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(8.dp))

            // Comunidad a la que pertenece
            Text(
                text = "#${post.comunidad}",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
 */