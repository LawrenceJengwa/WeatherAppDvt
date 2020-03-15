package com.lawrence.weatherappdvt.network.response


import com.google.gson.annotations.SerializedName
import com.lawrence.weatherappdvt.network.response.modal.forecast.*

data class ForecastWeatherResponse(
    @SerializedName("list")
    val weatherDetail: List<WeatherDetail>,
    @SerializedName("message")
    val message: Int
)