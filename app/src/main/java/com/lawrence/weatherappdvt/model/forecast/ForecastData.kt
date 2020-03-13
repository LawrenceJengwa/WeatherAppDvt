package com.lawrence.weatherappdvt.model.forecast

import java.util.*

data class ForecastData(val day: String = "",
                        val icon: String = "",
                        val temp: Double = 0.0,
                        val dateCreated: Date = Date()) {
}