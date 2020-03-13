package com.lawrence.weatherappdvt.network.response.modal.forecast

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)