package com.lawrence.weatherappdvt.core


object Constants {
//openWeatherMap api
    object NetworkService {
        const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
        const val API_KEY_VALUE = "ee069c197dad98bf2745020c421534ce"
        const val RATE_LIMITER_TYPE = "data"
        const val API_KEY_QUERY = "appid"
    }
//Places Api
    object AlgoliaKeys {
        const val APPLICATION_ID = "plU46DPCDVB9"
        const val SEARCH_API_KEY = "baf8f6c79e79232b162ab594da11091f"
    }

    object Coords {
        const val LAT = "lat"
        const val LON = "lon"
        const val METRIC = "metric"
    }
}
