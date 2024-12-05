package com.dripemporium.weather.data

import android.util.Log
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

    suspend fun getWeather(date: String, location: String, apiKey: String): HistoryResponse? {
        return try {
            val response = apiService.getHistoricalWeather(apiKey, location, date)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("getWeather", "HTTP error: ${response.code()}")
                null
            }
        } catch (e: HttpException) {
            Log.e("getWeather", "HTTP exception", e)
            null
        } catch (e: Exception) {
            Log.e("getWeather", "Exception", e)
            null
        }
    }
}