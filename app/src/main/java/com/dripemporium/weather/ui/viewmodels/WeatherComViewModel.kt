package com.dripemporium.weather.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dripemporium.weather.data.Forecast
import com.dripemporium.weather.data.WeatherComRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherComViewModel @Inject constructor(
    private val repository: WeatherComRepository
) : ViewModel() {

    private val _weatherData = MutableStateFlow<List<Forecast>?>(null)
    val weatherData: StateFlow<List<Forecast>?> = _weatherData

    private val _selectedDayWeather = MutableStateFlow<Forecast?>(null)
    val selectedDayWeather: StateFlow<Forecast?> = _selectedDayWeather

    fun fetchWeather(postalKey: String, apiKey: String) {
        viewModelScope.launch {
            _weatherData.value = repository.fetchWeather(postalKey, apiKey)?.forecasts
        }
    }

    fun selectDayWeather(dayIndex: Int) {
        _selectedDayWeather.value = _weatherData.value?.getOrNull(dayIndex)
    }

    fun convertToCelsius(fahrenheit: Int): Int = ((fahrenheit - 32) * 5) / 9
}