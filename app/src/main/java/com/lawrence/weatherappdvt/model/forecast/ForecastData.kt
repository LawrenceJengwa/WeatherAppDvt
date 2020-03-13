package com.lawrence.weatherappdvt.model.forecast

import java.util.*

data class ForecastData(
    val day: String = "",
    val city: String = "",
    val icon: String = "",
    val temp: Double = 0.0,
    val min: Double = 0.0,
    val max: Double = 0.0,
    val timeCreated: Date = Date()

) {
}