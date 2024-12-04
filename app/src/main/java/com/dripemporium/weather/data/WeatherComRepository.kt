package com.dripemporium.weather.data

import com.dripemporium.weather.network.WeatherComApiService
import javax.inject.Inject

class WeatherComRepository @Inject constructor(private val apiService: WeatherComApiService) {
    suspend fun fetchWeather(postalKey: String, apiKey: String): WeatherComResponse? {
        val response = apiService.getWeather(postalKey, apiKey = apiKey)
        return if (response.isSuccessful) response.body() else null
    }
}