package com.example.evaluacion2.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.evaluacion2.navigation.SetupNavGraph
import com.example.evaluacion2.ui.theme.Evaluacion2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Evaluacion2Theme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}