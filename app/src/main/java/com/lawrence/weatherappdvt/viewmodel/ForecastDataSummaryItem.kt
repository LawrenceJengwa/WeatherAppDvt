package com.lawrence.weatherappdvt.viewmodel

import java.util.*

data class ForecastDataSummaryItem(val temp: Double,
                                   val min: Double,
                                   val max: Double,
                                   val timeCreated: Date,
                                   val day: String) {
}