package dev.ghoni.bisya.ui.screen.camera

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CameraViewModel (
    context: Context,
    fileName: String
) : ViewModel() {
    private var cameraProvider: ProcessCameraProvider? = null
    private lateinit var interpreter: Interpreter

    init {
        try {
            interpreter = Interpreter(loadModelFile(context = context, fileName = fileName))
        } catch (e: Exception) {
            Log.e("CameraViewModel", "Error initializing Interpreter", e)
        }
    }

    fun bindCameraUseCase(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        cameraSelector: CameraSelector,
        previewView: PreviewView
    ) {
        viewModelScope.launch {
            try {
                cameraProvider = context.getCameraProvider()

                // Unbind all use cases before binding new ones
                cameraProvider?.unbindAll()

                // Build and bind the preview use case
                val previewUseCase = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                // Build and bind the imageAnalysis use case
                val imageAnalysisUseCase = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalysisUseCase.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                    val inputImageBuffer = convertImageProxyToByteBuffer(imageProxy)

                    // Run inference with the TensorFlow Lite model
                    val outputBuffer = ByteBuffer.allocateDirect(104)
                    interpreter.run(inputImageBuffer, outputBuffer)

                    // Process the outputBuffer and make predictions

                    // Example: Assuming a classification task with probabilities
                    val probabilities = getProbabilitiesFromOutputBuffer(outputBuffer)
                    val predictedClass = probabilities.indexOfFirst { it == probabilities.maxOrNull() }

                    // Update UI or perform further actions based on predictions
                    Log.d("CameraViewModel", "Predicted Class: $predictedClass")

                    // Close the imageProxy to release system resources
                    imageProxy.close()
                }

                cameraProvider?.bindToLifecycle(
                    lifecycleOwner, cameraSelector, previewUseCase, imageAnalysisUseCase
                )
            } catch (ex: Exception) {
                Log.e("CameraViewModel", "Use case binding failed", ex)
            }
        }
    }

    override fun onCleared() {
        // Release resources, e.g., close the Interpreter
        interpreter.close()
        cameraProvider?.unbindAll()
        super.onCleared()
    }

    private fun loadModelFile(context: Context, fileName: String): ByteBuffer {
        val assetManager = context.assets
        val inputStream = assetManager.open(fileName)

        // Read the model file into a ByteBuffer
        val modelBuffer = inputStream.readBytes()

        val buffer = ByteBuffer.allocateDirect(modelBuffer.size).apply {
            order(ByteOrder.nativeOrder())
            put(modelBuffer)
        }

        return buffer
    }

    // Function to convert ImageProxy to ByteBuffer
    private fun convertImageProxyToByteBuffer(imageProxy: ImageProxy): ByteBuffer {
        val planes = imageProxy.planes
        val yBuffer = planes[0].buffer

        // Note: Adjust the size based on your model's input requirements
        val inputSize = 12
        val inputImageBuffer = ByteBuffer.allocateDirect(inputSize)

        // Only copy the required bytes into the inputImageBuffer
        yBuffer.get(inputImageBuffer.array(), 0, inputSize)

        return inputImageBuffer
    }

    // Function to process the output buffer and get probabilities
    // Function to process the output buffer and get probabilities
    private fun getProbabilitiesFromOutputBuffer(outputBuffer: ByteBuffer): FloatArray {
        // Adjust the size based on your model's output tensor size
        val outputSize = 104

        // Ensure that the output buffer has the correct capacity
        require(outputBuffer.capacity() == outputSize) {
            "Output buffer size mismatch. Expected: $outputSize, Actual: ${outputBuffer.capacity()}"
        }

        // Create a FloatArray to store the probabilities
        val probabilities = FloatArray(outputSize / 4) // Assuming each probability is a 4-byte float

        // Rewind the output buffer before reading
        outputBuffer.rewind()

        // Read the values into the probabilities array
        outputBuffer.asFloatBuffer().get(probabilities)

        return probabilities
    }

    private suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { future ->
            future.addListener({
                continuation.resume(future.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }
}

class CameraViewModelFactory(private val context: Context, private val fileName: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CameraViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CameraViewModel(context, fileName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}