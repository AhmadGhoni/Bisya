package dev.ghoni.bisya.ui.screen.camera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.ghoni.bisya.ui.component.CameraButton

@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val fileName = "model.tflite"
    val viewModel: CameraViewModel = viewModel(
        factory = remember(context, fileName) {
            CameraViewModelFactory(context, fileName)
        }
    )

    DisposableEffect(Unit) {
        requestCameraPermission(context)
        onDispose {
            // Cleanup, if needed
        }
    }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
    ){
        AndroidView(
            modifier = modifier,
            factory = { context ->
                val previewView = PreviewView(context).apply {
                    this.scaleType = scaleType
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
                viewModel.bindCameraUseCase(context, lifecycleOwner, cameraSelector, previewView)
                previewView
            }
        )
        CameraButton()
    }
}

private fun requestCameraPermission(context: Context) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            (context as Activity),
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSION
        )
    }
}

// Define your camera permission request code
private const val REQUEST_CAMERA_PERMISSION = 123