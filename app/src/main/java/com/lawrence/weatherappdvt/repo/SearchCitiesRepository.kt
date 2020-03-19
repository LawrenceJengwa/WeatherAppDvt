package com.lawrence.weatherappdvt.repo

import NetworkBoundResource
import androidx.lifecycle.LiveData
import com.lawrence.weatherappdvt.core.Constants.NetworkService.RATE_LIMITER_TYPE
import com.lawrence.weatherappdvt.db.entity.CitiesForSearchEntity
import com.lawrence.weatherappdvt.domain.datasource.searchCities.SearchCitiesLocalDataSource
import com.lawrence.weatherappdvt.domain.datasource.searchCities.SearchCitiesRemoteDataSource
import com.lawrence.weatherappdvt.domain.model.SearchResponse
import com.lawrence.weatherappdvt.utils.domain.RateLimiter
import com.lawrence.weatherappdvt.utils.domain.Resource
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject



class SearchCitiesRepository @Inject constructor(
    private val searchCitiesLocalDataSource: SearchCitiesLocalDataSource,
    private val searchCitiesRemoteDataSource: SearchCitiesRemoteDataSource
) {

    private val rateLimiter = RateLimiter<String>(1, TimeUnit.SECONDS)

    fun loadCitiesByCityName(cityName: String?): LiveData<Resource<List<CitiesForSearchEntity>>> {
        return object : NetworkBoundResource<List<CitiesForSearchEntity>, SearchResponse>() {
            override fun saveCallResult(item: SearchResponse) = searchCitiesLocalDataSource.insertCities(item)

            override fun shouldFetch(data: List<CitiesForSearchEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<CitiesForSearchEntity>> = searchCitiesLocalDataSource.getCityByName(cityName)

            override fun createCall(): Single<SearchResponse> = searchCitiesRemoteDataSource.getCityWithQuery(
                cityName
                    ?: ""
            )

            override fun onFetchFailed() = rateLimiter.reset(RATE_LIMITER_TYPE)
        }.asLiveData
    }
}
