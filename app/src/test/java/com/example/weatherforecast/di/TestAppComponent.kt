package com.example.weatherforecast.di

import dagger.Component


@Component(modules = [ApiModule::class])
interface TestComponent : AppComponent {
    fun inject(test: WeatherRepoTest?)
}