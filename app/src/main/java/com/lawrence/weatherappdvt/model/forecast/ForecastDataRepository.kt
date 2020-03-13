package com.lawrence.weatherappdvt.model.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

class ForecastDataRepository {

    private val savedForecastData = mutableMapOf<Date, ForecastData>()
    private val liveData = MutableLiveData<List<ForecastData>>()

    fun saveForecastData(tc: ForecastData) {
        savedForecastData[tc.timeCreated] = tc
        liveData.value = savedForecastData.values.toList()
    }

    fun clearForecastData() {
        savedForecastData.clear()
        liveData.value = savedForecastData.values.toList()
    }

    fun loadForecastDataByDt(createDate: Date): ForecastData? {
        return savedForecastData[createDate]
    }

    fun loadForecastData(): LiveData<List<ForecastData>> {
        liveData.value = savedForecastData.values.toList()
        return liveData
    }

    fun mergeLocalDataList(dataList: List<ForecastData>) {
        dataList.forEach { forecastData ->
            savedForecastData[forecastData.timeCreated] = forecastData
        }
        liveData.value = savedForecastData.values.toList()
    }
}