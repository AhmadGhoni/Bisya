package dev.ghoni.bisya.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.ghoni.bisya.BisyaApp
import dev.ghoni.bisya.ui.screen.camera.CameraScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(route = Screen.Main.route) {
            BisyaApp(navController = navController)
        }
        composable(route = Screen.Camera.route) {
            CameraScreen()
        }
    }
}