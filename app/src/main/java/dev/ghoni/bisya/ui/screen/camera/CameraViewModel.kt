package dev.ghoni.bisya.ui.screen.camera

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ghoni.bisya.core.repository.CameraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CameraViewModel: ViewModel() {
    private val _predictionState = MutableStateFlow("")
    val predictionState: StateFlow<String> = _predictionState

    private val _isClientConnected = MutableStateFlow(false)
    val isClientConnected: StateFlow<Boolean> = _isClientConnected

    fun getPrediction(cameraRepository: CameraRepository) {
        viewModelScope.launch {
            try {
                val response = cameraRepository.fetchPredict()
                if (response.isSuccessful) {
                    val predictionResponse = response.body()
                    val prediction = predictionResponse?.prediction ?: ""
                    _predictionState.value = prediction
                }
            } catch (e: Exception) {
                Log.e("getPrediction","Error",e)
            }
        }
    }

    fun isClient(connect: Boolean) {
        _isClientConnected.value = connect
    }
}