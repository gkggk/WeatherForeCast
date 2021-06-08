package com.example.weatherforecast.di

import com.acs.accelerate.utils.NetworkHelper
import com.example.weatherforecast.repo.WeatherRepo
import com.example.weatherforecast.ui.App
import org.mockito.Mockito




class TestAPiModule(private val app: App) : ApiModule(app) {
    fun provideMyPrinter(): WeatherRepo? {
        return Mockito.mock(WeatherRepo::class.java)
    }

    fun provideRestService(): NetworkHelper? {
        return Mockito.mock(NetworkHelper::class.java)
    }
}