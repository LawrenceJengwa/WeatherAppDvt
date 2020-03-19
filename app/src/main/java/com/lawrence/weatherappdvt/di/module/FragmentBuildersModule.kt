package com.lawrence.weatherappdvt.di.module

import com.lawrence.weatherappdvt.ui.dashboard.DashboardFragment
import com.lawrence.weatherappdvt.ui.search.SearchFragment
import com.lawrence.weatherappdvt.ui.splash.SplashFragment
import com.lawrence.weatherappdvt.ui.weather_detail.WeatherDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): DashboardFragment

    @ContributesAndroidInjector
    abstract fun contributeWeatherDetailFragment(): WeatherDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment
}
