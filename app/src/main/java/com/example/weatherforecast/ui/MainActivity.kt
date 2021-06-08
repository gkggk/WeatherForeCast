package com.example.weatherforecast.ui

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.acs.accelerate.utils.LocationHelper
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.ActivityMainBinding
import com.example.weatherforecast.helper.PermissionHelper
import com.example.weatherforecast.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    val model: MainViewModel by viewModels()
    private val locationHelper = LocationHelper(this)
    private lateinit var activityMainBinding: ActivityMainBinding

    @Inject
    lateinit var periodicWorkRequest: PeriodicWorkRequest

    lateinit var workManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.appComponent.inject(this)

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        workManager = WorkManager.getInstance(this)

        addObservers()
        fetchWeatherReportFromLocal()
        addWorkManagerListener()
        startWorkManager()
        checkForActiveNetwork()
    }

    private fun addObservers() {
        model.location.observe(
            this, { location ->
                if (location != null) {
                    model.fetchWeatherReport()
                }
            })
    }

    private fun fetchWeatherReportFromLocal() {
        model.fetchWeatherReportFromLocal().observe(
            this, {
                it?.let {
                    activityMainBinding.weather = it
                }
            })
    }

    private fun checkForLocationPermissions() {
        if (PermissionHelper.checkMyPermission(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                LOCATION_PERMISSION_REQUESTCODE
            )
        ) {
            model.getLastKnownLocation(locationHelper)
        }
    }

    private fun checkForActiveNetwork() {
        if (!model.isConnectedToNetwork()) {
            val snackbar = Snackbar
                .make(
                    findViewById(R.id.rootLayout),
                    R.string.no_network,
                    Snackbar.LENGTH_INDEFINITE
                )
            snackbar.setAction("OK") {}
            snackbar.show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUESTCODE) {
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                model.getLastKnownLocation(locationHelper)
            } else {
                checkForLocationPermissions()
            }
        }
    }

    private fun addWorkManagerListener() {
        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.getId())
            .observe(this, {
                it?.let {
                    if (it.state == WorkInfo.State.RUNNING) {
                        checkForLocationPermissions()
                    }
                }
            })
    }

    private fun startWorkManager() {
        workManager.enqueue(periodicWorkRequest)
    }

    companion object {
        const val LOCATION_PERMISSION_REQUESTCODE = 1000
    }

    override fun onDestroy() {
        super.onDestroy()
        workManager.cancelWorkById(periodicWorkRequest.id)
    }
}