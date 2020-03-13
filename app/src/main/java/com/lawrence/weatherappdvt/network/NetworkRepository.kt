package com.lawrence.weatherappdvt.network

import android.app.Activity
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import com.lawrence.weatherappdvt.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkRepository {

    private val baseUrl = "https://api.openweathermap.org"
    val appId = "5ef6e8587a34b60c06d8752456fbe90a"

    private val client = OkHttpClient().newBuilder()
            .cache(null)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            })
            .build()
    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    val weatherService: WeatherService = retrofit.create(WeatherService::class.java)

}