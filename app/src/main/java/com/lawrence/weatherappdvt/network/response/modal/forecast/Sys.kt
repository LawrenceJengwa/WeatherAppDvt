package com.lawrence.weatherappdvt.network.response.modal.forecast

import com.google.gson.annotations.SerializedName


data class Sys(
    @SerializedName("pod")
    val pod: String
)
