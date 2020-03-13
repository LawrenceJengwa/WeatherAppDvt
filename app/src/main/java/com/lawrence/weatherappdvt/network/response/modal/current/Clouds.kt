package com.lawrence.weatherappdvt.network.response.modal.current

import com.google.gson.annotations.SerializedName

data class Clouds(

	@field:SerializedName("all")
	val all: Int? = null
)