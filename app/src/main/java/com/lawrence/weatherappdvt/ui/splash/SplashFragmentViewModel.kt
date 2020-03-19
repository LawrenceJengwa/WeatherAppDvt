package com.lawrence.weatherappdvt.ui.splash

import android.content.SharedPreferences
import com.lawrence.weatherappdvt.core.BaseViewModel
import javax.inject.Inject



class SplashFragmentViewModel @Inject constructor(var sharedPreferences: SharedPreferences) : BaseViewModel() {
    var navigateDashboard = false
}
