package com.lawrence.weatherappdvt.network.response.modal.forecast

import com.google.gson.annotations.SerializedName


data class WeatherDetail(
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("dt_txt")
    val dtTxt: String,
    @SerializedName("main")
    val main: Main,
    @SerializedName("rain")
    val rain: Rain,
    @SerializedName("snow")
    val snow: Snow,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
)