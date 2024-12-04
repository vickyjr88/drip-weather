package com.dripemporium.weather.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dripemporium.weather.data.Location
import com.dripemporium.weather.data.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    private val repository: LocationRepository
) : ViewModel() {

    private val _locationDetail = MutableStateFlow<Location?>(null)
    val locationDetail: StateFlow<Location?> = _locationDetail

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    val apiKey = "cf4c8f9b677d4bf997362513240412"

    fun fetchLocationDetail(locationId: String) {
        viewModelScope.launch {
            try {
                Log.d("LocationDetailViewModel", "Fetching location detail for ID: $locationId")
                val location = repository.getLocationDetail(apiKey, locationId)
                _locationDetail.value = location?.location
                Log.d("LocationDetailViewModel", "Location detail fetched: $location")
            } catch (e: HttpException) {
                _error.value = e.message
                Log.e("LocationDetailViewModel", "Error fetching location detail", e)
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("LocationDetailViewModel", "Error fetching location detail", e)
            }
        }
    }
}