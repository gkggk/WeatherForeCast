package com.example.weatherforecast.di

import com.example.weatherforecast.repo.WeatherRepo
import com.example.weatherforecast.ui.MainActivity
import com.example.weatherforecast.viewmodel.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [(ApiModule::class),(WorkModule::class)])
@Singleton
interface AppComponent {
    fun inject(repo: WeatherRepo)
    fun inject(repo: MainViewModel)
    fun inject(mainActivity: MainActivity)
}