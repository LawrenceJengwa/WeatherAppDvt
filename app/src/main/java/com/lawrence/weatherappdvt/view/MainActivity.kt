package com.lawrence.weatherappdvt.view

import android.content.Context
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.lawrence.weatherappdvt.R

class MainActivity : AppCompatActivity() {

    companion object {
        //if for some reason location is null
        //manually set the latitude & longitude to Joburg coordinates
        var lat: Double = -26.2
        var lon: Double = 28.04
    }
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            var fusedLocationClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        lat = location.latitude!!
                    }
                    if (location != null) {
                        lon = location.longitude!!

                    }
                }


        }

    fun loadData(view: View) {
        val intent = Intent(this, WeatherDataActivity::class.java).apply {
        }
        startActivity(intent)
    }
}