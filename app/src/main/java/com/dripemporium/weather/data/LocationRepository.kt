package com.dripemporium.weather.data

import android.util.Log
import retrofit2.HttpException
import javax.inject.Inject

class LocationRepository @Inject constructor(private val apiService: WeatherApiService) {

    suspend fun getLocationDetail(apiKey: String, locationId: String): LocationDetailsResponse? {
        return try {
            val response = apiService.getLocationDetail(apiKey, "id:${locationId}")
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("LocationRepository", "HTTP error: ${response.code()}")
                null
            }
        } catch (e: HttpException) {
            Log.e("LocationRepository", "HTTP exception", e)
            null
        } catch (e: Exception) {
            Log.e("LocationRepository", "Exception", e)
            null
        }
    }
}