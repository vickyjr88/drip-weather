package com.dripemporium.weather.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiService: WeatherApiService,
    // private val weatherDao: WeatherDao
) {
    suspend fun fetchWeather(apiKey: String, location: String): WeatherResponse? {
        return try {
            val response = apiService.getCurrentWeather(apiKey = apiKey, location = location)
            if (response.isSuccessful) response.body() else null
        } catch (e: HttpException) {
            // Handle HTTP exception
            null
        } catch (e: Exception) {
            // Handle other exceptions
            null
        }
    }

    suspend fun getLocations(apiKey: String, query: String): List<LocationResponse> =
        withContext(Dispatchers.IO) {
            apiService.searchLocations(apiKey, query)
        }

   /* suspend fun getWeather(date: String, location: String, apiKey: String): WeatherData {
        val cachedData = weatherDao.getWeather(date)
        return cachedData ?: apiService.getHistoricalWeather(apiKey, location, date).also {
            weatherDao.insertWeather(it)
        }
    }*/
}