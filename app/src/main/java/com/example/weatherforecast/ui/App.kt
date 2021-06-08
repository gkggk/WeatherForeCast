package com.example.weatherforecast.ui

import android.app.Application
import com.example.weatherforecast.di.ApiModule
import com.example.weatherforecast.di.AppComponent
import com.example.weatherforecast.di.DaggerAppComponent
import com.example.weatherforecast.di.WorkModule


/**
 * This class provides a convenient reference point for application level context.
 */
class App : Application() {
    companion object {
        lateinit var instance: App
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .apiModule(ApiModule(this))
            .workModule(WorkModule(this))
            .build()
    }

}