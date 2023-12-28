package dev.ghoni.bisya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.ghoni.bisya.ui.screen.camera.CameraScreen
import dev.ghoni.bisya.ui.screen.camera.CameraViewModel
import dev.ghoni.bisya.ui.theme.BisyaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BisyaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val cameraViewModel: CameraViewModel by viewModels()
                    CameraScreen(viewModel = cameraViewModel)
                }
            }
        }
    }
}