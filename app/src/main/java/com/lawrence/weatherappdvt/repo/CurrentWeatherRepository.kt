package com.lawrence.weatherappdvt.repo

import NetworkBoundResource
import androidx.lifecycle.LiveData
import com.lawrence.weatherappdvt.core.Constants.NetworkService.RATE_LIMITER_TYPE
import com.lawrence.weatherappdvt.db.entity.CurrentWeatherEntity
import com.lawrence.weatherappdvt.domain.datasource.currentWeather.CurrentWeatherLocalDataSource
import com.lawrence.weatherappdvt.domain.datasource.currentWeather.CurrentWeatherRemoteDataSource
import com.lawrence.weatherappdvt.domain.model.CurrentWeatherResponse
import com.lawrence.weatherappdvt.utils.domain.RateLimiter
import com.lawrence.weatherappdvt.utils.domain.Resource
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class CurrentWeatherRepository @Inject constructor(
    private val currentWeatherRemoteDataSource: CurrentWeatherRemoteDataSource,
    private val currentWeatherLocalDataSource: CurrentWeatherLocalDataSource
) {

    private val currentWeatherRateLimit = RateLimiter<String>(30, TimeUnit.SECONDS)

    fun loadCurrentWeatherByGeoCords(lat: Double, lon: Double, fetchRequired: Boolean, units: String): LiveData<Resource<CurrentWeatherEntity>> {
        return object : NetworkBoundResource<CurrentWeatherEntity, CurrentWeatherResponse>() {
            override fun saveCallResult(item: CurrentWeatherResponse) = currentWeatherLocalDataSource.insertCurrentWeather(item)

            override fun shouldFetch(data: CurrentWeatherEntity?): Boolean = fetchRequired

            override fun loadFromDb(): LiveData<CurrentWeatherEntity> = currentWeatherLocalDataSource.getCurrentWeather()

            override fun createCall(): Single<CurrentWeatherResponse> = currentWeatherRemoteDataSource.getCurrentWeatherByGeoCords(lat, lon, units)

            override fun onFetchFailed() = currentWeatherRateLimit.reset(RATE_LIMITER_TYPE)
        }.asLiveData
    }
}
