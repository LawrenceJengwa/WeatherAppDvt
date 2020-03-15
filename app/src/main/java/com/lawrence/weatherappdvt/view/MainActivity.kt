package com.lawrence.weatherappdvt.view

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.lawrence.weatherappdvt.R
import com.lawrence.weatherappdvt.databinding.ActivityMainBinding
import com.lawrence.weatherappdvt.databinding.FutureDetailDialogBinding
import com.lawrence.weatherappdvt.model.forecast.ForecastData
import com.lawrence.weatherappdvt.network.response.ForecastWeatherResponse
import com.lawrence.weatherappdvt.viewmodel.ForecastDataViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{
    lateinit var binding: ActivityMainBinding
    lateinit var placeName: TextView
    companion object {
        //if for some reason location is null
        //manually set the latitude & longitude to Joburg coordinates
        var lat: Double = 28.04363
        var lon: Double = -26.20227
        var areaName:String = ""
        const val PERMISSION_REQUEST_CODE = 1001
        const val PLAY_SERVICE_RESOLUTION_REQUEST = 1000
    }

    var mGoogleApiClient:GoogleApiClient?=null
    var mLocationRequest:LocationRequest?=null
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
            binding.forecast = ViewModelProviders.of(this).get(ForecastDataViewModel::class.java)
            val vm = ViewModelProviders.of(this).get(ForecastDataViewModel::class.java)
            //setSupportActionBar(binding.toolbar)
            recyclerView2.setHasFixedSize(false)
            val adapter = ForecastDataAdapter { item -> showDialog(vm.forecastDataProcessor.loadForecastDataByDt(item.timeCreated)!!) }
            recyclerView2.adapter = adapter
            vm.loadSavedForecastDataSummaries().observe(this, Observer {
                if (it!!.isNotEmpty()) {
                    recyclerView2.visibility = View.VISIBLE
                    progressBarMain.visibility = View.GONE
                    adapter.updateList(it)
                    } else {
                    if (progressBarMain.visibility != View.VISIBLE)
                       // textViewAdd.visibility = View.VISIBLE
                    adapter.updateList(it)
                }

                requestPermission()
                if (checkPlayService())
                    buildGoogleApiClient()

            })
        }

    override fun onStart() {
        super.onStart()
        if (mGoogleApiClient != null){
            mGoogleApiClient!!.connect()
        }
    }

    override fun onResume() {
        checkPlayService()
        super.onResume()
    }

    override fun onDestroy() {
        mGoogleApiClient!!.disconnect()
        super.onDestroy()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestPermission(

    ) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ){

            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (checkPlayService()){
                        buildGoogleApiClient()
                    }
                }
            }
        }
    }

    private fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API).build()
    }

    private fun checkPlayService(): Boolean {
        val resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)
        if (resultCode != ConnectionResult.SUCCESS){
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICE_RESOLUTION_REQUEST).show()
            }
            else{
                Toast.makeText(applicationContext, "This device is not supported", Toast.LENGTH_SHORT).show()
                finish()
            }
            return false
        }
        return true
    }

    override fun onConnected(p0: Bundle?) {
       createLocationRequest()
    }

    private fun createLocationRequest() {
       mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 1000
        mLocationRequest!!.fastestInterval = 5000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ){
            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
    }

    override fun onConnectionSuspended(p0: Int) {
        mGoogleApiClient!!.connect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.i("ERROR: ", "Connection failed" +p0.errorCode)
    }

    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            lat = location.latitude
            Log.i("location", lat.toString())
            lon = location.longitude
//            val geocoder = Geocoder(this, Locale.getDefault())
//            val addresses: List<Address> = geocoder.getFromLocation(lat, lon, 1)
//            val areaName: String = addresses[0].adminArea
//            val exactAddressName: String = addresses[0].featureName
        }else{
            Toast.makeText(this, "Can not retrieve location", Toast.LENGTH_SHORT).show()
        }

    }
    fun loadData(view: View) {
        val intent = Intent(this, WeatherDataActivity::class.java).apply {
            putExtra("Latitude", lat)
            putExtra("Longitude", lon)
        }
        startActivity(intent)
    }

    private fun showDialog(forecastData: ForecastData) {
        val dialog = Dialog(this)
        val futureDetailDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.future_detail_dialog, null, false) as FutureDetailDialogBinding
        futureDetailDialogBinding.forecastData = forecastData
        dialog.setContentView(futureDetailDialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()
    }


}