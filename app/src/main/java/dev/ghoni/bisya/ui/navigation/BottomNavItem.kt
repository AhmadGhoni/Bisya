package dev.ghoni.bisya.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItem(val title: String, val icon: ImageVector, val screen: String) {
    Home("Home", Icons.Default.Home, "home"),
    Ext("Extensions", Icons.Default.Place, "ext"),
    Profile("Profile", Icons.Default.Person, "profile")
}