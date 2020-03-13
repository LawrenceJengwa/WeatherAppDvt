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
    /*fun gertForecastData(onWeatherDataListener: OnWeatherDataListener) {

        val call = networkRepository.weatherService.getForecast(DeviceLocation.latitude, DeviceLocation.longitude, networkRepository.appId)
        call.enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(call: Call<ForecastResponse>?, response: Response<ForecastResponse>?) {
                if (response != null && response.isSuccessful) {
                    val weatherItem = response.body()
                    onWeatherDataListener.onSuccess(WeatherData(
                        weatherItem?.city?.name!!, weatherItem.weatherDetail[0].weather[0].icon,
                        weatherItem.weatherDetail[0].main.feelsLike.toString(),
                        weatherItem.weatherDetail[0].main.tempMin,
                        weatherItem.weatherDetail[0].main.tempMax.toString()
                    ))
                } else {
                    onFailure(call, Throwable("Bad Response"))
                }
            }

            override fun onFailure(call: Call<ForecastResponse>?, t: Throwable?) {
                Log.e("Response Failure", t?.message, t)
            }
        })
    }*/

}