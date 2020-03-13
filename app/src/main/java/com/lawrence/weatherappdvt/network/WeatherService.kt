package com.lawrence.weatherappdvt.network

import com.lawrence.weatherappdvt.network.response.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/data/2.5/weather")
    fun getWeather(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("appid") appId: String): Call<WeatherResponse>
    @GET("/data/2.5/forecast")
    fun getForecast(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("appid") appId: String): Call<WeatherResponse>
}