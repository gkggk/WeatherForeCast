package com.example.weatherforecast.helper

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Worker class which triggers every 2 hours to reminding of fetching latest weather updates
 */
class WeatherWorker(
    appContext: Context,
    params: WorkerParameters
) : Worker(appContext, params) {

    override fun doWork(): Result {
        return Result.success()
    }
}
