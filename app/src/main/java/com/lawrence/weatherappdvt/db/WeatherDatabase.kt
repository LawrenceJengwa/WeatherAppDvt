package com.lawrence.weatherappdvt.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lawrence.weatherappdvt.db.dao.CitiesForSearchDao
import com.lawrence.weatherappdvt.db.dao.CurrentWeatherDao
import com.lawrence.weatherappdvt.db.dao.ForecastDao
import com.lawrence.weatherappdvt.db.entity.CitiesForSearchEntity
import com.lawrence.weatherappdvt.db.entity.CurrentWeatherEntity
import com.lawrence.weatherappdvt.db.entity.ForecastEntity
import com.lawrence.weatherappdvt.utils.typeconverters.DataConverter

@Database(
    entities = [
        ForecastEntity::class,
        CurrentWeatherEntity::class,
        CitiesForSearchEntity::class
    ],
    version = 2
)
@TypeConverters(DataConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao

    abstract fun currentWeatherDao(): CurrentWeatherDao

    abstract fun citiesForSearchDao(): CitiesForSearchDao
}
