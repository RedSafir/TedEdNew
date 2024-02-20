package com.miftah.tededexpert.core.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class ResultResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
