package com.lawrence.weatherappdvt.ui.dashboard

import com.lawrence.weatherappdvt.core.BaseViewState
import com.lawrence.weatherappdvt.db.entity.CurrentWeatherEntity
import com.lawrence.weatherappdvt.utils.domain.Status



class CurrentWeatherViewState(
    val status: Status,
    val error: String? = null,
    val data: CurrentWeatherEntity? = null
) : BaseViewState(status, error) {
    fun getForecast() = data
}
