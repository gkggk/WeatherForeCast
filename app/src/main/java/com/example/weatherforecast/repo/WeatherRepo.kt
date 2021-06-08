package com.example.weatherforecast.repo

import android.location.Location
import com.acs.accelerate.utils.NetworkHelper
import com.example.weatherforecast.db.AppDatabase
import com.example.weatherforecast.di.APIService
import com.example.weatherforecast.helper.PreferencesHelper
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.ui.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 *
 * Weather Repository class which manages weathers information from dba and server
 */

class WeatherRepo() {
    @Inject
    lateinit var apiService: APIService

    @Inject
    lateinit var networkHelper: NetworkHelper

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    @Inject
    lateinit var calendar: Calendar

    init {
        App.appComponent.inject(this)
    }

    /**
     *
     * Fetches weather from server is there is active internet connection
     * If there is no active internet connection, feches weather from local db
     */
    suspend fun fetchWeather(location: Location): Weather? {
        if (!networkHelper.isNetworkConnectionAvailable()) {
            return fetchWeatherFromLocalDB()
        }

        val response = apiService.getCurrentWeather(
            location.latitude.toString(),
            location.longitude.toString(),
            "78b292fab5d6d6f990851ce33660612b"
        ).execute()
        return response.body()
    }

    /**
     * Stores weather data in loca Database
     */
    fun storeWeatherInDB(weather: Weather) {
        GlobalScope.launch {
            val i = AppDatabase.getInstance().weather().insertAll(weather)
            weather.main?.let {
                AppDatabase.getInstance().main().insertAll(it)
            }
            weather.coord?.let {
                AppDatabase.getInstance().coord().insertAll(it)
            }
            preferencesHelper.lastCallTime = calendar.timeInMillis
        }
    }

    /**
     *
     * Fetches Weather data from local database
     */
    suspend fun fetchWeatherFromLocalDB(): Weather? {
        return withContext(Dispatchers.IO) {
            val coord = AppDatabase.getInstance().coord().getTopRow()
            val main = AppDatabase.getInstance().main().getTopRow()
            val weather = AppDatabase.getInstance().weather().getTopRow()

            weather?.let {
                coord?.let { weather.coord = it }
                main?.let { weather.main = it }
            }
            weather
        }

    }
}