package com.example.weatherforecast.di

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import com.example.weatherforecast.helper.PreferencesHelper
import com.example.weatherforecast.helper.WeatherWorker
import com.example.weatherforecast.ui.App
import dagger.Module
import dagger.Provides
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 *
 * Provides all dependencies related to work manager
 */
@Module
class WorkModule(private val app: App) {
    val _HourMillis = 1000 * 60 * 60
    val intervalPeriodInHours: Long = 2

    @Provides
    @Singleton
    fun giveApplicationContext(): App = app

    @Provides
    fun giveConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }

    @Provides
    @Singleton
    fun givePreferences(): PreferencesHelper {
        return PreferencesHelper(app)

    }

    @Provides
    @Singleton
    fun giveCalendar(): Calendar {
        return Calendar.getInstance()
    }

    @Provides
    fun giveWorkRequest(
        preferencesHelper: PreferencesHelper,
        calendar: Calendar,
        constraints: Constraints
    ): PeriodicWorkRequest {
        var delay: Long = 0
        if (preferencesHelper.lastCallTime != (-1.0).toLong()) {
            val diff = calendar.timeInMillis - preferencesHelper.lastCallTime
            val hours = diff / (_HourMillis)
            if (hours < intervalPeriodInHours) {
                delay = (intervalPeriodInHours * _HourMillis) - diff
            }
        }

        return PeriodicWorkRequestBuilder<WeatherWorker>(intervalPeriodInHours, TimeUnit.HOURS)
            .setConstraints(constraints)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()
    }


}