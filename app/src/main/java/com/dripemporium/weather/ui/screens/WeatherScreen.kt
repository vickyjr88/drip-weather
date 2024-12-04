package com.dripemporium.weather.ui.screens

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dripemporium.weather.ui.viewmodels.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel()) {
    val weatherData by viewModel.weatherData.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        weatherData?.let { data ->
            Text("Location: ${data.location.name}, ${data.location.country}")
            Text("Temperature: ${data.current.temp_c}°C")
            Text("Condition: ${data.current.condition.text}")
        } ?: run {
            CircularProgressIndicator()
        }
    }
}