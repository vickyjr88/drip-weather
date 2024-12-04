package com.dripemporium.weather.network

import com.dripemporium.weather.data.WeatherComResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherComApiService {
    @GET("/v3/wx/forecast/daily/5day")
    suspend fun getWeather(
        @Query("postalKey") postalKey: String,
        @Query("format") format: String = "json",
        @Query("apiKey") apiKey: String
    ): Response<WeatherComResponse>
}