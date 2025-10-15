package com.agudelo.newapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.agudelo.newapp.ui.theme.NewAppTheme
import com.agudelo.newapp.ui.theme.NewsDetailsScreen
import com.agudelo.newapp.ui.theme.NewsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        NewsScreen(navController = navController)
                    }

                    // ✅ Sin navArgument: leemos el payload del savedStateHandle
                    composable("details") {
                        val payload = navController
                            .previousBackStackEntry
                            ?.savedStateHandle
                            ?.get<Map<String, String>>("article_payload")

                        NewsDetailsScreen(
                            url = payload?.get("url") ?: "",
                            title = payload?.get("title") ?: "",
                            description = payload?.get("description") ?: "",
                            imageUrl = payload?.get("image") ?: ""
                        )
                    }
                }
            }
        }
    }
}
