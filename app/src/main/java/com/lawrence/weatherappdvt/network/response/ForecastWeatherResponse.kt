package com.lawrence.weatherappdvt.network.response


import com.google.gson.annotations.SerializedName
import com.lawrence.weatherappdvt.network.response.modal.forecast.*

data class ForecastWeatherResponse(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val weatherDetail: List<WeatherDetail>,
    @SerializedName("message")
    val message: Int
)