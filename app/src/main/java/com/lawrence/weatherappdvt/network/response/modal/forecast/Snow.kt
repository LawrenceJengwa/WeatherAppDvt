package com.lawrence.weatherappdvt.network.response.modal.forecast

import com.google.gson.annotations.SerializedName


data class Snow(
    @SerializedName("3h")
    val h: Double
)