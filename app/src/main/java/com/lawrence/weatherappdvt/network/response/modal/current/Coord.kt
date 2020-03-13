package com.lawrence.weatherappdvt.network.response.modal.current

import com.google.gson.annotations.SerializedName


data class Coord(

        @field:SerializedName("lon")
        val lon: Double? = null,

        @field:SerializedName("lat")
        val lat: Double? = null
)