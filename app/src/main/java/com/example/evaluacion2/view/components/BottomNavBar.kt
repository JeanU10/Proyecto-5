package com.example.evaluacion2.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.evaluacion2.navigation.Screen

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : BottomNavItem(Screen.Home.route, Icons.Default.Home, "Home")
    object Communities : BottomNavItem(Screen.Communities.route, Icons.Default.Apps, "Comunidades")
    object Create : BottomNavItem(Screen.CreatePost.route, Icons.Default.AddCircle, "Crear")
    object Metrics : BottomNavItem(Screen.MetricsList.route, Icons.Default.BarChart, "Métricas")
    object Profile : BottomNavItem(Screen.Profile.route, Icons.Default.Person, "Perfil")
}

@Composable
fun BottomNavBar(
    navController: NavController,
    currentRoute: String?
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Communities,
        BottomNavItem.Create,
        BottomNavItem.Metrics,
        BottomNavItem.Profile
    )

    NavigationBar(
        containerColor = Color.White
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.LightGray
                )
            )
        }
    }
}