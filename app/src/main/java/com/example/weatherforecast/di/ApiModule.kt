package com.example.weatherforecast.di

import com.acs.accelerate.utils.NetworkHelper
import com.example.weatherforecast.helper.PreferencesHelper
import com.example.weatherforecast.repo.WeatherRepo
import com.example.weatherforecast.ui.App
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
open class ApiModule(private val app: App) {
    private val BASE_URL = "http://api.openweathermap.org/"

    @Provides
    @Singleton
    fun giveApplicationContext(): App = app

    @Provides
    @Singleton
    fun giveRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun giveAPIService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    @Provides
    @Singleton
    fun giveWeatherRepo(): WeatherRepo {
        return WeatherRepo()
    }

    @Provides
    @Singleton
    fun giveNetworkHelper(): NetworkHelper {
        return NetworkHelper()
    }

    @Provides
    @Singleton
    fun giveOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    }

}