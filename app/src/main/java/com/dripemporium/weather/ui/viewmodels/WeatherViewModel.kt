package com.dripemporium.weather.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dripemporium.weather.data.WeatherRepository
import com.dripemporium.weather.data.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData

    fun fetchWeather(location: String, apiKey: String) {
        viewModelScope.launch {
            try {
                _weatherData.value = repository.fetchWeather(apiKey, location);
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}