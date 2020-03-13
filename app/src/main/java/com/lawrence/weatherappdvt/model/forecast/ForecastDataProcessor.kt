package com.lawrence.weatherappdvt.model.forecast


import android.util.Log
import androidx.lifecycle.LiveData
import com.lawrence.weatherappdvt.network.NetworkRepository
import com.lawrence.weatherappdvt.network.response.ForecastWeatherResponse
import com.lawrence.weatherappdvt.view.MainActivity.Companion.lat
import com.lawrence.weatherappdvt.view.MainActivity.Companion.lon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    fun gertForecastData(onForecastDataListener: OnForecastDataListener ) {

        val call = networkRepository.weatherService.getForecast(lat, lon, networkRepository.appId)
        call.enqueue(object : Callback<ForecastWeatherResponse> {
            override fun onResponse(call: Call<ForecastWeatherResponse>?, response: Response<ForecastWeatherResponse>?) {
                if (response != null && response.isSuccessful) {
                    val weatherItem = response.body()
                    onForecastDataListener.onSuccess(ForecastData(
                        weatherItem?.city?.name!!, weatherItem.weatherDetail[0].weather[0].icon,
                        weatherItem.weatherDetail[0].main.feelsLike.toString(),
                        weatherItem.weatherDetail[0].main.tempMin,
                        weatherItem.weatherDetail[0].main.tempMax
                    ))
                } else {
                    onFailure(call, Throwable("Bad Response"))
                }
            }

            override fun onFailure(call: Call<ForecastWeatherResponse>?, t: Throwable?) {
                Log.e("Response Failure", t?.message, t)
            }
        })
    }
}



