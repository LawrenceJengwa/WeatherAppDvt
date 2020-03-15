package com.lawrence.weatherappdvt.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.lawrence.weatherappdvt.model.forecast.ForecastData
import com.lawrence.weatherappdvt.model.forecast.ForecastDataProcessor
import com.lawrence.weatherappdvt.utils.DBHelper

class ForecastDataViewModel @JvmOverloads constructor(app: Application, val forecastDataProcessor:  ForecastDataProcessor = ForecastDataProcessor()) :
        ObservableViewModel(app), ForecastDataProcessor.OnForecastDataListener {
        var lastForecastData = ForecastData()
        var forecastDataListener: ForecastDataProcessor.OnForecastDataListener? = null
        val db by lazy { DBHelper(app) }

        init {
            forecastDataListener = this
            forecastDataProcessor.getForecastData(this)
        }

        override fun onSuccess(forecastData: ForecastData) {
            forecastDataProcessor.saveForecastData(forecastData)
            updateOutputs(forecastData)
            db.insertForecastData(forecastData)
        }

        fun updateOutputs(fd: ForecastData) {
            lastForecastData = fd
            notifyChange()
        }

        fun loadSavedForecastDataSummaries(): LiveData<List<ForecastDataSummaryItem>> {
           forecastDataProcessor.mergeLocalDataList(db.readForecastData())
            return Transformations.map(forecastDataProcessor.loadForecastData()) { weatherDataObjects ->
                weatherDataObjects.map {
                    ForecastDataSummaryItem(it.temp, it.min, it.max,
                        it.timeCreated, it.day)
                }
            }
        }
}