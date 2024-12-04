package com.dripemporium.weather.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_data")
data class WeatherData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tarehe: String,
    val temperature: Float,
    val humidity: Int,
    val windSpeed: Float,
    val condition: String
)