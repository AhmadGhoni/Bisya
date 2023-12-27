package dev.ghoni.bisya.core.model.remote

import com.google.gson.annotations.SerializedName

data class ModelResponse(
	@field:SerializedName("ModelResponse")
	val modelResponse: List<ModelResponseItem>
)

data class ModelResponseItem(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("createdBy")
	val createdBy: String,

	@field:SerializedName("version")
	val version: String,

	@field:SerializedName("file")
	val file: String,
)
