package com.example.weatherforecast.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecast.model.Main

@Dao
interface MainDao {
    @Query("SELECT * FROM Main LIMIT 1")
    fun getTopRow(): Main?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(main: Main)
}