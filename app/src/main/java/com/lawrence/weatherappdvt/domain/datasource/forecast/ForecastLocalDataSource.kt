package com.lawrence.weatherappdvt.domain.datasource.forecast

import com.lawrence.weatherappdvt.db.dao.ForecastDao
import com.lawrence.weatherappdvt.db.entity.ForecastEntity
import com.lawrence.weatherappdvt.domain.model.ForecastResponse
import javax.inject.Inject



class ForecastLocalDataSource @Inject constructor(private val forecastDao: ForecastDao) {

    fun getForecast() = forecastDao.getForecast()

    fun insertForecast(forecast: ForecastResponse) = forecastDao.deleteAndInsert(ForecastEntity(forecast))
}
