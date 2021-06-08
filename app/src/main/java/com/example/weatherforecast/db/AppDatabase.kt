package com.example.weatherforecast.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherforecast.model.Coord
import com.example.weatherforecast.model.Main
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.ui.App

@Database(entities = arrayOf(Weather::class, Main::class, Coord::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weather(): WeatherDao
    abstract fun main(): MainDao
    abstract fun coord(): CoordDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(): AppDatabase {
            if (instance == null)
            //Creates a database object with help of ROOM by name of WeatherDB
                instance = Room.databaseBuilder(
                    App.instance, AppDatabase::class.java,
                    "WeatherDB"
                ).build()

            return instance!!
        }
    }
}