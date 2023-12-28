package dev.ghoni.bisya.ui.navigation

sealed class Screen(val route: String, val title: String) {
    object Main : Screen("main", "Main")
    object Camera : Screen("camera", "Camera")
}