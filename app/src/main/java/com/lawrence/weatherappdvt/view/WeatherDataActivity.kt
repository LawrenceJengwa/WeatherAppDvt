package com.lawrence.weatherappdvt.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lawrence.weatherappdvt.R
import com.lawrence.weatherappdvt.databinding.ActivityWeatherDataBinding
import com.lawrence.weatherappdvt.databinding.DetailDialogBinding
import com.lawrence.weatherappdvt.model.current.WeatherData
import com.lawrence.weatherappdvt.viewmodel.WeatherDataViewModel
import kotlinx.android.synthetic.main.content_weatherdata.*


class WeatherDataActivity : AppCompatActivity() {

    lateinit var binding: ActivityWeatherDataBinding

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_weather_data_processor, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                getWeatherData(binding.root)
                true
            }
            R.id.action_clear -> {
                binding.vm?.weatherDataProcessor?.clearWeatherData()
                binding.vm?.db?.deleteAllData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getWeatherData(view: View) {
        binding.vm?.weatherDataProcessor?.getWeatherData(binding.vm?.weatherDataListener!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather_data)
        binding.vm = ViewModelProviders.of(this).get(WeatherDataViewModel::class.java)
        val vm = ViewModelProviders.of(this).get(WeatherDataViewModel::class.java)
        //setSupportActionBar(binding.toolbar)
        recyclerView.setHasFixedSize(true)
        val adapter = WeatherDataAdapter { item -> showDialog(vm.weatherDataProcessor.loadWeatherDataByDt(item.dateCreated)!!) }
        recyclerView.adapter = adapter
        vm.loadSavedWeatherDataSummaries().observe(this, Observer {
            if (it!!.isNotEmpty()) {
                recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                textViewAdd.visibility = View.GONE
                adapter.updateList(it)
            } else {
                if (progressBar.visibility != View.VISIBLE)
                    textViewAdd.visibility = View.VISIBLE
                adapter.updateList(it)
            }
        })
    }

    private fun showDialog(weather: WeatherData) {
        val dialog = Dialog(this)
        val detailDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.detail_dialog, null, false) as DetailDialogBinding
        detailDialogBinding.weatherData = weather
        dialog.setContentView(detailDialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()
    }
}