package com.dripemporium.weather.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dripemporium.weather.data.Forecast
import com.dripemporium.weather.ui.viewmodels.WeatherComViewModel

@Composable
fun MainScreen(viewModel: WeatherComViewModel = hiltViewModel()) {
    val weatherData by viewModel.weatherData.collectAsState()
    val selectedDayWeather by viewModel.selectedDayWeather.collectAsState()
    var isCelsius by remember { mutableStateOf(false) }
    var postalKey by remember { mutableStateOf("12345") } // Example postal key
    val apiKey = "your_api_key_here"

    LaunchedEffect(Unit) {
        viewModel.fetchWeather(postalKey, apiKey)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Weather Forecast",
            style = MaterialTheme.typography.h3
        )

        // Weather list for the week
        weatherData?.let { forecasts ->
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(forecasts.size) { index ->
                    WeatherCard(
                        forecast = forecasts[index],
                        isCelsius = isCelsius,
                        onClick = { viewModel.selectDayWeather(index) }
                    )
                }
            }
        } ?: CircularProgressIndicator()

        // Selected Day Weather Details
        selectedDayWeather?.let { forecast ->
            Text(
                text = "Selected Day Weather:",
                style = MaterialTheme.typography.h4
            )
            Text(text = "Condition: ${forecast.narrative}")
            Text(
                text = "Temperature: ${
                    if (isCelsius) "${viewModel.convertToCelsius(forecast.temperatureMax)} °C"
                    else "${forecast.temperatureMax} °F"
                }"
            )
        }

        // Celsius/Fahrenheit Toggle
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Fahrenheit")
            Switch(
                checked = isCelsius,
                onCheckedChange = { isCelsius = it }
            )
            Text(text = "Celsius")
        }
    }
}

@Composable
fun WeatherCard(
    forecast: Forecast,
    isCelsius: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
            .size(width = 100.dp, height = 150.dp),
        elevation = 2.dp

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = forecast.dayOfWeek, style = MaterialTheme.typography.body1)
            Text(
                text = if (isCelsius) "${forecast.convertToCelsius()} °C"
                else "${forecast.temperatureMax} °F",
                style = MaterialTheme.typography.body1
            )
            Text(text = forecast.narrative, style = MaterialTheme.typography.body2)
        }
    }
}

fun Forecast.convertToCelsius(): Int = ((temperatureMax - 32) * 5) / 9