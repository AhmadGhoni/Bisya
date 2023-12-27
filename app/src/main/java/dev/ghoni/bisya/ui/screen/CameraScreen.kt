package dev.ghoni.bisya.ui.screen

import android.Manifest
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CameraScreen() {
    val context = LocalContext.current
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isPermissionGranted ->
        if (isPermissionGranted) {
           //Grant
        } else {
            // Permission denied, handle accordingly (e.g., show a message)
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    // Request camera permission when the composable is first launched
    DisposableEffect(context) {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)

        // Dispose the effect to run only once
        onDispose { }
    }

    // Box is now placed inside the @Composable function
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Set background color as needed
    ) {
        // Add camera preview content here (placeholder for now)
        Text(
            text = "Camera Preview",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
@Composable
fun PreviewCamera() {
    val context = LocalContext.current
    Column {

        AndroidView(factory = {

            PreviewView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

        }) { view ->

            // Update logic goes here

        }

    }

}