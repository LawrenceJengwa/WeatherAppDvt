package com.lawrence.weatherappdvt.ui.weather_detail.weatherHourOfDay

import androidx.databinding.ObservableField
import com.lawrence.weatherappdvt.core.BaseViewModel
import com.lawrence.weatherappdvt.domain.model.ListItem
import javax.inject.Inject



class WeatherHourOfDayItemViewModel @Inject internal constructor() : BaseViewModel() {
    var item = ObservableField<ListItem>()
}
