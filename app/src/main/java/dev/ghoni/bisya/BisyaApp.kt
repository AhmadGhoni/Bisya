package dev.ghoni.bisya

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dev.ghoni.bisya.ui.component.BottomBar
import dev.ghoni.bisya.ui.navigation.BottomNavItem
import dev.ghoni.bisya.ui.screen.ExtScreen
import dev.ghoni.bisya.ui.screen.HomeScreen
import dev.ghoni.bisya.ui.screen.camera.CameraScreen
import dev.ghoni.bisya.ui.screen.camera.CameraViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BisyaApp(modifier: Modifier = Modifier, navController : NavController, cameraViewModel: CameraViewModel) {
    val (currentScreen, setCurrentScreen) = remember { mutableStateOf(BottomNavItem.Home) }
    Scaffold(
        bottomBar = {
            BottomBar(
                modifier = modifier,
                currentScreen = currentScreen,
                onItemSelected = { selectedScreen -> setCurrentScreen(selectedScreen) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
        ) {
            when (currentScreen) {
                BottomNavItem.Home -> {
                    HomeScreen(title = "Home")
                }
                BottomNavItem.Ext ->{
                    ExtScreen(title = "Extensions")
                }
                BottomNavItem.Profile-> {
                    CameraScreen(viewModel = cameraViewModel)
                }
            }
        }
    }
}