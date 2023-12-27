package dev.ghoni.bisya.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarItem(
    val title: String,
    val icon: ImageVector,
    val screen: BottomNavItem)