package dev.ghoni.bisya.ui.screen.camera

import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import dev.ghoni.bisya.ui.component.CameraButton

@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    viewModel: CameraViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current
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