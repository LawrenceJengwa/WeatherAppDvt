package com.lawrence.weatherappdvt.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.lawrence.weatherappdvt.model.current.WeatherData
import com.lawrence.weatherappdvt.model.forecast.ForecastData
import java.util.*

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.w("DB", "Upgrade from version $oldVersion to $newVersion")
        Log.w("DB", "This is version 1, no DB to update")
    }
//Current
    private val TABLE_CURRENT = "WeatherData"
    private val COL_DATE_CREATED = "dateCreated"
    private val COL_NAME = "name"
    private val COL_COUNTRY = "country"
    private val COL_DESCRIPTION = "description"
    private val COL_TEMP = "temp"
    private val COL_DT = "dt"

    //Forecast
    private val TABLE_FORECAST = "FocastData"
    private val COL_TIME_CREATED = "timeCreated"
    private val COL_CITY = "city"
    private val COL_ICON = "icon"
    private val COL_DAY ="day"
    private val COL_MIN_TEMP = "min"
    private val COL_MAX_TEMP = "max"
    private val COL_CURRENT_TEMP = "temp"


    companion object {
        private val DATABASE_NAME = "SQLITE_DATABASE"
        private val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_CURRENT ($COL_DATE_CREATED LONG PRIMARY KEY , $COL_NAME" +
                "  VARCHAR(256),$COL_COUNTRY  VARCHAR(256),$COL_DESCRIPTION  VARCHAR(256),$COL_TEMP " +
                " DOUBLE, $COL_DT  VARCHAR(256))"
        db?.execSQL(createTable)

        val createForecastTable = "CREATE TABLE $TABLE_FORECAST ($COL_TIME_CREATED LONG PRIMARY KEY , $COL_CITY" +
                "  VARCHAR(256), $COL_DAY " + " VARCHAR(256), $COL_MIN_TEMP  DOUBLE,$COL_MAX_TEMP  " +
                "DOUBLE,$COL_CURRENT_TEMP " +
                " DOUBLE)"
        db?.execSQL(createForecastTable)
    }


    fun insertData(weatherData: WeatherData) {
        val sqliteDB = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_DATE_CREATED, weatherData.dateCreated.time)
        contentValues.put(COL_NAME, weatherData.name)
        contentValues.put(COL_COUNTRY, weatherData.country)
        contentValues.put(COL_DESCRIPTION, weatherData.description)
        contentValues.put(COL_TEMP, weatherData.temp)
        contentValues.put(COL_DT, weatherData.dt)
        val result = sqliteDB.insert(TABLE_CURRENT, null, contentValues)
    }
    fun insertForecastData(forecastData: ForecastData) {
        val sqliteDB = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TIME_CREATED, forecastData.timeCreated.time)
        contentValues.put(COL_DAY, forecastData.day)
        contentValues.put(COL_CITY, forecastData.city)
        contentValues.put(COL_MIN_TEMP, forecastData.min)
        contentValues.put(COL_MAX_TEMP, forecastData.max)
        contentValues.put(COL_CURRENT_TEMP, forecastData.temp)
        val resultForecast = sqliteDB.insert(TABLE_FORECAST, null, contentValues)
    }

    fun readData(): MutableList<WeatherData> {
        val weatherList = mutableListOf<WeatherData>()
        val sqliteDB = readableDatabase
        val query = "SELECT * FROM $TABLE_CURRENT"
        val result = sqliteDB.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val weatherData =
                    WeatherData(
                        name = result.getString(result.getColumnIndex(COL_NAME)),
                        country = result.getString(result.getColumnIndex(COL_COUNTRY)),
                        description = result.getString(result.getColumnIndex(COL_DESCRIPTION)),
                        temp = result.getDouble(result.getColumnIndex(COL_TEMP)),
                        dt = result.getString(result.getColumnIndex(COL_DT)),
                        dateCreated = Date(result.getLong(result.getColumnIndex(COL_DATE_CREATED)))
                    )
                weatherList.add(weatherData)
            } while (result.moveToNext())
        }
        result.close()
        sqliteDB.close()
        return weatherList
    }
    fun readForecastData(): MutableList<ForecastData> {
        val forecastList = mutableListOf<ForecastData>()
        val sqliteDB = readableDatabase
        val query = "SELECT * FROM $TABLE_FORECAST"
        val resultForecast = sqliteDB.rawQuery(query, null)
        if (resultForecast.moveToFirst()) {
            do {
                val forecastData =
                    ForecastData(
                        temp = resultForecast.getDouble(resultForecast.getColumnIndex(COL_TEMP)),
                        min = resultForecast.getDouble(resultForecast.getColumnIndex(COL_MIN_TEMP)),
                        max = resultForecast.getDouble(resultForecast.getColumnIndex(COL_MAX_TEMP)),
                        day = resultForecast.getString(resultForecast.getColumnIndex(COL_DAY)),
                        city = resultForecast.getString(resultForecast.getColumnIndex(COL_CITY)),
                        timeCreated = Date(resultForecast.getLong(resultForecast.getColumnIndex(COL_TIME_CREATED)))

                        )
                    forecastList.add(forecastData)
            } while (resultForecast.moveToNext())
        }
        resultForecast.close()
        sqliteDB.close()
        return forecastList
    }

    fun deleteAllData() {
        val sqliteDB = writableDatabase
        sqliteDB.delete(TABLE_CURRENT, null, null)
        sqliteDB.delete(TABLE_FORECAST, null, null)
        sqliteDB.close()
    }
}