package com.example.weatherforecast.di

import com.example.weatherforecast.model.Weather
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    /**
     * Fetches current weather for the provided latitude and longitude values
     */
    @GET("data/2.5/weather")
    fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String
    ): Call<Weather>
}