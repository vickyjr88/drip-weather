package com.dripemporium.weather.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dripemporium.weather.data.HistoryResponse
import com.dripemporium.weather.data.WeatherData
import com.dripemporium.weather.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HistWeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    private val _weather = MutableStateFlow<HistoryResponse?>(null)
    val historyData: StateFlow<HistoryResponse?> = _weather

    fun fetchHistoricalWeather(date: String, location: String, apiKey: String) {
        try {
            viewModelScope.launch {
                _weather.value = repository.getWeather(date, location, apiKey)
            }
             Log.d("HistWeatherViewModel", "Location detail fetched: $location")
        } catch (e: HttpException) {
            // _error.value = e.message
            Log.e("HistWeatherViewModel", "Error fetching location detail", e)
        } catch (e: Exception) {
            // _error.value = e.message
            Log.e("HistWeatherViewModel", "Error fetching location detail", e)
        }
    }
}