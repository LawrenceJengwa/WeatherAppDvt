package com.lawrence.weatherappdvt.ui.dashboard.forecast

import androidx.databinding.ObservableField
import com.lawrence.weatherappdvt.core.BaseViewModel
import com.lawrence.weatherappdvt.domain.model.ListItem
import java.util.*
import javax.inject.Inject



class ForecastItemViewModel @Inject internal constructor() : BaseViewModel() {
    var item = ObservableField<ListItem>()
}
