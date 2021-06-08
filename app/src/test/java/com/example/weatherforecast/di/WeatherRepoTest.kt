package com.example.weatherforecast.di

import android.location.Location
import com.acs.accelerate.utils.NetworkHelper
import com.example.weatherforecast.repo.WeatherRepo
import com.example.weatherforecast.ui.App
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class WeatherRepoTest {

    @Rule
    @JvmField var mockitoRule = MockitoJUnit.rule()


    lateinit var weatherRepo: WeatherRepo

    @Mock
    lateinit var location: Location

    @Mock
    lateinit var networkHelper: NetworkHelper

    @Mock
    lateinit var appComponent: AppComponent

    @Before
    fun setUp() {
//        val component: TestComponent = DaggerAppComponent.builder()
//            .myModule(TestModule()).build()
//        component.inject(this)
//        DaggerAppComponent

        MockitoAnnotations.initMocks(this)
        weatherRepo= WeatherRepo()
        App.appComponent=appComponent
    }


    @Test
    fun fetchWeather_whenNoInternet() {
        Mockito.`when`(networkHelper.isNetworkConnectionAvailable()).thenReturn(true)
        Mockito.`when`(location.latitude).thenReturn(35.0)
        Mockito.`when`(location.latitude).thenReturn(139.0)
        runBlocking {
            val weather = weatherRepo.fetchWeather(location)
            Assert.assertNotNull(weather)
            Assert.assertEquals(weather!!.name,"Shuzenji")
        }
    }
}