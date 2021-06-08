package com.example.weatherforecast.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acs.accelerate.utils.LocationHelper
import com.acs.accelerate.utils.NetworkHelper
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.repo.WeatherRepo
import com.example.weatherforecast.ui.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel : ViewModel() {

    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var weatherRepo: WeatherRepo

    @Inject
    lateinit var networkHelper: NetworkHelper

    var location = MutableLiveData<Location>().apply { value = null }
    private var weather = MutableLiveData<Weather>().apply { value = null }

    /**
     * Fectahes weather report from server and stores in local database
     */
    fun fetchWeatherReport() {
        location.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val w = withContext(Dispatchers.Default) {
                    weatherRepo.fetchWeather(it)
                }
                w?.let {
                    weatherRepo.storeWeatherInDB(it)
                    withContext(Dispatchers.Main) {
                        weather.value = it
                    }
                }
            }
        }
    }

    /**
     * Fectahes weather report from local database
     */
    fun fetchWeatherReportFromLocal(): MutableLiveData<Weather> {
        viewModelScope.launch(Dispatchers.IO) {
            val w = withContext(Dispatchers.Default) {
                weatherRepo.fetchWeatherFromLocalDB()
            }
            w?.let {
                withContext(Dispatchers.Main) {
                    weather.value = it
                }
            }
        }
        return weather
    }

    /**
     * Gives last known location
     * If location service is enabled, uses Fused location provider
     * If location service is disabled, uses Location Manager
     */
    fun getLastKnownLocation(locationHelper: LocationHelper) {
        try {
            if (networkHelper.isNetworkConnectionAvailable())
                viewModelScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.IO) {
                        locationHelper.fetchLastKnownLocation {
                            location.value = it
                        }
                    }
                }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    }

    /**
     * Check if there is active internet connect on the device at the moment
     */
    fun isConnectedToNetwork(): Boolean = networkHelper.isNetworkConnectionAvailable()
}