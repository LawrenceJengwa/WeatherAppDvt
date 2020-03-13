package com.lawrence.weatherappdvt.model.forecast


import androidx.lifecycle.LiveData
import com.lawrence.weatherappdvt.network.NetworkRepository
import java.util.*

class ForecastDataProcessor(private val repository: ForecastDataRepository = ForecastDataRepository(),
                            private val networkRepository: NetworkRepository = NetworkRepository()) {

    interface OnForecastDataListener {
        fun onSuccess(forecastData: ForecastData)
    }

    fun saveForecastData(fD: ForecastData) {
        repository.saveForecastData(fD)
    }

    fun clearForecastData() {
        repository.clearForecastData()
    }

    fun mergeLocalDataList(dataList: List<ForecastData>) {
        repository.mergeLocalDataList(dataList)
    }

    fun loadForecastDataByDt(createDate: Date): ForecastData? {
        return repository.loadForecastDataByDt(createDate)
    }

    fun loadForecastData(): LiveData<List<ForecastData>> {
        return repository.loadForecastData()
    }
}