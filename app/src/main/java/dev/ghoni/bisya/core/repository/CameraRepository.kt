package dev.ghoni.bisya.core.repository

import dev.ghoni.bisya.core.api.ApiConfig
import dev.ghoni.bisya.core.model.remote.PredictRespon
import retrofit2.Response

class CameraRepository {
    suspend fun fetchPredict(): Response<PredictRespon> {
        return ApiConfig.getApiService().getPrediction()
    }
}