package com.lawrence.weatherappdvt.model.current

import android.util.Log
import androidx.lifecycle.LiveData
import com.lawrence.weatherappdvt.network.NetworkRepository
import com.lawrence.weatherappdvt.network.response.CurrentWeatherResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class WeatherDataProcessor(private val repository: WeatherDataRepository = WeatherDataRepository(), private val networkRepository: NetworkRepository = NetworkRepository()) {

    interface OnWeatherDataListener {
        fun onSuccess(weatherData: WeatherData)
    }

    fun saveWeatherData(tc: WeatherData) {
        repository.saveWeatherData(tc)
    }

    fun clearWeatherData() {
        repository.clearWeatherData()
    }

    fun mergeLocalDataList(dataList: List<WeatherData>) {
        repository.mergeLocalDataList(dataList)
    }

    fun loadWeatherDataByDt(createDate: Date): WeatherData? {
        return repository.loadWeatherDataByDt(createDate)
    }

    fun loadWeatherData(): LiveData<List<WeatherData>> {
        return repository.loadWeatherData()
    }


    fun getWeatherData(onWeatherDataListener: OnWeatherDataListener) {

        val call = networkRepository.weatherService.getWeather(-26.20227, 28.04363, networkRepository.appId)
        call.enqueue(object : Callback<CurrentWeatherResponse> {
            override fun onResponse(call: Call<CurrentWeatherResponse>?, responseCurrent: Response<CurrentWeatherResponse>?) {
                if (responseCurrent != null && responseCurrent.isSuccessful) {
                    val weatherItem = responseCurrent.body()
                    onWeatherDataListener.onSuccess(
                        WeatherData(
                            weatherItem?.name!!,
                            weatherItem.sys?.country!!, weatherItem.weather?.get(0)?.description!!,
                            weatherItem.main?.temp!!, weatherItem.dt.toString()
                        )
                    )
                } else {
                    onFailure(call, Throwable("Bad Response"))
                }
            }

            override fun onFailure(call: Call<CurrentWeatherResponse>?, t: Throwable?) {
                Log.e("Response Failure", t?.message, t)
            }
        })
    }

}