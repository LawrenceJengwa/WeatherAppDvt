package com.lawrence.weatherappdvt.ui.search.result

import androidx.databinding.ObservableField
import com.lawrence.weatherappdvt.core.BaseViewModel
import com.lawrence.weatherappdvt.db.entity.CitiesForSearchEntity
import javax.inject.Inject



class SearchResultItemViewModel @Inject internal constructor() : BaseViewModel() {
    var item = ObservableField<CitiesForSearchEntity>()
}
