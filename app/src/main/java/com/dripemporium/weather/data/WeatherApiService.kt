package com.dripemporium.weather.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("current.json") // Endpoint for current weather
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("aqi") aqi: String = "no" // Air Quality Index disabled by default
    ): Response<WeatherResponse>

    @GET("search.json")
    suspend fun searchLocations(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): List<LocationResponse>

    @GET("current.json")
    suspend fun getLocationDetail(
        @Query("key") apiKey: String,
        @Query("q") locationId: String
    ): Response<LocationDetailsResponse>

/*    @GET("history.json")
    suspend fun getHistoricalWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("dt") date: String
    ): WeatherData*/
}