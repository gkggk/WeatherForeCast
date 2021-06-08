package com.example.weatherforecast.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecast.model.Coord

@Dao
interface CoordDao {
    @Query("SELECT * FROM Coord LIMIT 1")
    fun getTopRow(): Coord?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(coord: Coord)
}