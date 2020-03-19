package com.lawrence.weatherappdvt.ui.weather_detail

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lawrence.weatherappdvt.core.BaseViewModel
import com.lawrence.weatherappdvt.db.entity.ForecastEntity
import com.lawrence.weatherappdvt.domain.datasource.forecast.ForecastLocalDataSource
import com.lawrence.weatherappdvt.domain.model.ListItem
import javax.inject.Inject



class WeatherDetailViewModel @Inject constructor(private val forecastLocalDataSource: ForecastLocalDataSource) : BaseViewModel() {

    var weatherItem: ObservableField<ListItem> = ObservableField()
    private var forecastLiveData: LiveData<ForecastEntity> = MutableLiveData()
    var selectedDayDate: String? = null
    var selectedDayForecastLiveData: MutableLiveData<List<ListItem>> = MutableLiveData()

    fun getForecastLiveData() = forecastLiveData

    fun getForecast(): LiveData<ForecastEntity> {
        return forecastLocalDataSource.getForecast()
    }
}
