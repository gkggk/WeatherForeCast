package com.example.weatherforecast.helper

import android.content.Context
import android.content.SharedPreferences
import com.example.weatherforecast.ui.App
import java.util.*

/**
 *
 * Helper class to store manages all preferences in the app
 */
class PreferencesHelper(app: App) {
    private val PREFS_FILENAME = "com.priviamedicalgroup.priviaapp.prefs"
    var prefs: SharedPreferences = app.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    private val TIME = "LAST_CALL_TIME"


    var lastCallTime: Long
        get() = prefs.getLong(TIME, -1.0.toLong())
        set(value) = prefs.edit().putLong(TIME, value).apply()

}