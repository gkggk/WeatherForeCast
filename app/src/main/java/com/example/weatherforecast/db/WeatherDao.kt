package com.example.weatherforecast.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecast.model.Weather

@Dao
interface WeatherDao {
    @Query("SELECT * FROM Weather  LIMIT 1")
    fun getTopRow(): Weather?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(weather: Weather)
}