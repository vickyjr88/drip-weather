package com.dripemporium.weather.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dripemporium.weather.data.LocationResponse
import com.dripemporium.weather.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    val apiKey = "cf4c8f9b677d4bf997362513240412"

    private val _locations = MutableStateFlow<List<LocationResponse>>(emptyList())
    val locations: StateFlow<List<LocationResponse>> = _locations

    fun fetchLocations(query: String) {
        viewModelScope.launch {
            _locations.value = repository.getLocations(apiKey, query)
        }
    }
}