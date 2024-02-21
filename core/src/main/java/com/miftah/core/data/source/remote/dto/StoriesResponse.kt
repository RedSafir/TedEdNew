package com.miftah.core.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class StoriesResponse(

	@field:SerializedName("listStory")
	val listStory: List<ListStoryItemResponse> = emptyList(),

	@field:SerializedName("error")
	val error: Boolean = false,

	@field:SerializedName("message")
	val message: String = ""
)

data class ListStoryItemResponse(

	@field:SerializedName("photoUrl")
	val photoUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("lon")
	val lon: Double? = null,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("lat")
	val lat: Double? = null
)
