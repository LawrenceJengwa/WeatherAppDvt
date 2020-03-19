package com.lawrence.weatherappdvt.ui.dashboard


import com.lawrence.weatherappdvt.core.BaseViewState
import com.lawrence.weatherappdvt.db.entity.ForecastEntity
import com.lawrence.weatherappdvt.utils.domain.Status


class ForecastViewState(
    val status: Status,
    val error: String? = null,
    val data: ForecastEntity? = null
) : BaseViewState(status, error) {
    fun getForecast() = data
}
