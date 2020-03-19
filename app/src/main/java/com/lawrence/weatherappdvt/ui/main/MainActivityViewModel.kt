package com.lawrence.weatherappdvt.ui.main

import androidx.databinding.ObservableField
import com.lawrence.weatherappdvt.core.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject internal constructor() : BaseViewModel() {
    var toolbarTitle: ObservableField<String> = ObservableField()
}
