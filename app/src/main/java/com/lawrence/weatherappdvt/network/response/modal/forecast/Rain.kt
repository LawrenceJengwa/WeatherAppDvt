package com.lawrence.weatherappdvt.network.response.modal.forecast

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    val h: Double
)