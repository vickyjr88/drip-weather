/*
package com.dripemporium.weather.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_data WHERE tarehe = :tarehe")
    suspend fun getWeather(tarehe: String): WeatherData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(data: WeatherData): Long
}*/
