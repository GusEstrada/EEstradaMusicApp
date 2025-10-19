package com.example.eestradamusicapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eestradamusicapp.DetailScreen
import com.example.eestradamusicapp.HomeScreen
import com.example.eestradamusicapp.ui.theme.EEstradaMusicAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EEstradaMusicAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()


                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(navController)
                        }
                        composable(
                            route = "detail/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id") ?: return@composable
                            DetailScreen(id = id, navController = navController)
                        }
                    }
                }
            }
        }
    }
}
