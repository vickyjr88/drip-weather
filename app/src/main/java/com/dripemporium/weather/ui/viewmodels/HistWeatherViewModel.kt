package com.dripemporium.weather.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dripemporium.weather.data.WeatherData
import com.dripemporium.weather.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistWeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    private val _weather = MutableStateFlow<WeatherData?>(null)
    val weather: StateFlow<WeatherData?> = _weather

    fun fetchWeather(date: String, location: String, apiKey: String) {
        viewModelScope.launch {
            _weather.value = repository.getWeather(date, location, apiKey)
        }
    }
}