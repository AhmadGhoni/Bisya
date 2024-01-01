package dev.ghoni.bisya.core.utils

import android.graphics.Bitmap
import java.nio.ByteBuffer

fun preprocessImage(inputImageBuffer: ByteBuffer, bitmap: Bitmap) {
    // Resize the bitmap to match the model input size (e.g., 224x224 pixels)
    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)

    // Convert the resizedBitmap to ByteBuffer
    val intValues = IntArray(224 * 224)
    resizedBitmap.getPixels(intValues, 0, 224, 0, 0, 224, 224)

    inputImageBuffer.rewind()

    for (pixelValue in intValues) {
        val normalizedPixel = (pixelValue and 0xFF) / 255.0f
        inputImageBuffer.putFloat(normalizedPixel)
    }
}
