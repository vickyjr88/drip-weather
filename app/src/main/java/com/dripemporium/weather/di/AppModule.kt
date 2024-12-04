package com.dripemporium.weather.di

import com.dripemporium.weather.data.WeatherApiService
import com.dripemporium.weather.data.WeatherComRepository
import com.dripemporium.weather.network.WeatherComApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherComApiService(): WeatherComApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.weather.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherComApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherApiService(): WeatherApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(apiService: WeatherComApiService): WeatherComRepository {
        return WeatherComRepository(apiService)
    }
}