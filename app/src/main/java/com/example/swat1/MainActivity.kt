package com.example.swat1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.swat1.ui.theme.MyComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ExamPaperApp()
                }
            }
        }
    }
}

@Composable
fun ExamPaperApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") {
            CollapsingExamScreen(navController = navController)
        }
        composable(
            "exam_detail/{subject}",
            arguments = listOf(navArgument("subject") { type = NavType.StringType })
        ) { backStackEntry ->
            val subject = backStackEntry.arguments?.getString("subject") ?: ""
            ExamDetailScreen(subject = subject, navController = navController)
        }
    }
}
