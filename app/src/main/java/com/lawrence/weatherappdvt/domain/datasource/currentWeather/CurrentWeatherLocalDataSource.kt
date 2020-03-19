package com.lawrence.weatherappdvt.domain.datasource.currentWeather

import com.lawrence.weatherappdvt.db.dao.CurrentWeatherDao
import com.lawrence.weatherappdvt.db.entity.CurrentWeatherEntity
import com.lawrence.weatherappdvt.domain.model.CurrentWeatherResponse
import javax.inject.Inject


class CurrentWeatherLocalDataSource @Inject constructor(private val currentWeatherDao: CurrentWeatherDao) {

    fun getCurrentWeather() = currentWeatherDao.getCurrentWeather()

    fun insertCurrentWeather(currentWeather: CurrentWeatherResponse) = currentWeatherDao.deleteAndInsert(
        CurrentWeatherEntity(currentWeather)
    )
}
