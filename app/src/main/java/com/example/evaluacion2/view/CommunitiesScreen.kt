package com.example.evaluacion2.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.evaluacion2.controller.CommunityController
import com.example.evaluacion2.model.Community
import com.example.evaluacion2.model.EstadoComunidad
import com.example.evaluacion2.view.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunitiesScreen(navController: NavController) {
    val communityController = remember { CommunityController() }
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Destacadas", "Cerca", "Mis comunidades", "Nuevas")

    val comunidades = if (searchQuery.isBlank()) {
        communityController.filtrarPorCategoria(tabs[selectedTab])
    } else {
        communityController.buscarComunidades(searchQuery)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comunidades", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController, currentRoute = "communities")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0E0E0))
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar comunidades, etiquetas...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )

            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color(0xFFE0E0E0),
                edgePadding = 16.dp
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (selectedTab < 2) {
                    item {
                        Text(
                            "Sugeridas para ti",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                } else {
                    item {
                        Text(
                            "Mis comunidades",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }

                items(comunidades) { comunidad ->
                    CommunityCard(comunidad)
                }
            }
        }
    }
}

@Composable
fun CommunityCard(community: Community) {
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
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(community.nombre, fontWeight = FontWeight.Bold)
                    when (community.estado) {
                        EstadoComunidad.ABIERTA -> {
                            Surface(
                                color = Color(0xFF4CAF50),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    "Abierta",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    color = Color.White,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                        EstadoComunidad.AUTOGESTION -> {
                            Surface(
                                color = Color(0xFF4CAF50),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    "Autogestión",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    color = Color.White,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                        else -> {}
                    }
                }

                Text(
                    "${community.miembros}${if (community.distancia.isNotEmpty()) " • ${community.distancia}" else ""}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                if (community.rol != null) {
                    Text(
                        community.rol.name.lowercase().capitalize(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (community.rol == null) {
                        OutlinedButton(
                            onClick = { },
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Ver")
                        }
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                when (community.estado) {
                                    EstadoComunidad.ABIERTA, EstadoComunidad.AUTOGESTION -> "Unirse"
                                    EstadoComunidad.SOLICITAR_ACCESO -> "Solicitar acceso"
                                }
                            )
                        }
                    } else {
                        OutlinedButton(
                            onClick = { },
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Entrar")
                        }
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(if (community.rol.name == "MIEMBRO") "Encuesta" else "Publicar")
                        }
                    }
                }
            }
        }
    }
}