package dev.ghoni.bisya.ui.screen.camera

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CameraViewModel : ViewModel() {
    private var cameraProvider: ProcessCameraProvider? = null

    fun bindCameraUseCase(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        cameraSelector: CameraSelector,
        previewView: PreviewView
    ) {
        viewModelScope.launch {
            try {
                cameraProvider = context.getCameraProvider()
                cameraProvider?.unbindAll()

                val previewUseCase = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                cameraProvider?.bindToLifecycle(
                    lifecycleOwner, cameraSelector, previewUseCase
                )
            } catch (ex: Exception) {
                Log.e("CameraViewModel", "Use case binding failed", ex)
            }
        }
    }

    override fun onCleared() {
        cameraProvider?.unbindAll()
        super.onCleared()
    }

    suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { future ->
            future.addListener({
                continuation.resume(future.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }
}

