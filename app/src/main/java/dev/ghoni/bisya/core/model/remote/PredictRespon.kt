package dev.ghoni.bisya.core.model.remote

import com.google.gson.annotations.SerializedName

data class PredictRespon(

	@field:SerializedName("prediction")
	val prediction: String
)
