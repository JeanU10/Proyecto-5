package com.example.evaluacion2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.evaluacion2.view.*

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Communities : Screen("communities")
    object CreatePost : Screen("create_post")
    object MetricsList : Screen("metrics_list")
    object CommunityMetrics : Screen("community_metrics/{communityName}") {
        fun createRoute(communityName: String) = "community_metrics/$communityName"
    }
    object Profile : Screen("profile")
}

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(route = Screen.Register.route) {
            RegisterScreen(navController = navController)
        }

        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screen.Communities.route) {
            CommunitiesScreen(navController = navController)
        }

        composable(route = Screen.CreatePost.route) {
            CreatePostScreen(navController = navController)
        }

        composable(route = Screen.MetricsList.route) {
            MetricsListScreen(navController = navController)
        }

        composable(route = Screen.CommunityMetrics.route) { backStackEntry ->
            val communityName = backStackEntry.arguments?.getString("communityName") ?: ""
            CommunityMetricsScreen(navController = navController, communityName = communityName)
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}
