package dev.ghoni.bisya.core.api

import dev.ghoni.bisya.core.model.remote.PredictRespon
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("predict")
    suspend fun getPrediction(): Response<PredictRespon>
}