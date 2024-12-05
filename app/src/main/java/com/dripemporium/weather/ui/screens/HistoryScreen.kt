package com.dripemporium.weather.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dripemporium.weather.data.WeatherData

@Composable
fun HistoryScreen(
    historicalData: List<WeatherData>, // Replace with your data model
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            androidx.compose.material.TopAppBar(
                title = { Text("Historical Weather Data") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(historicalData) { data ->
                WeatherDataRow(data)
            }
        }
    }
}

@Composable
fun WeatherDataRow(data: WeatherData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        // elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Time: ${data.time ?: "N/A"}", style = MaterialTheme.typography.subtitle1)
            Text(text = "Temperature: ${data.temperature ?: "N/A"} °C", style = MaterialTheme.typography.body1)
            Text(text = "Humidity: ${data.humidity ?: "N/A"}%", style = MaterialTheme.typography.body1)
            Text(text = "Wind Speed: ${data.windSpeed ?: "N/A"} km/h", style = MaterialTheme.typography.body1)
            Text(text = "Condition: ${data.condition.text ?: "N/A"}", style = MaterialTheme.typography.body1)
        }
    }
}