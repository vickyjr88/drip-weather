package com.dripemporium.weather

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dripemporium.weather.ui.screens.HistoryScreen
import com.dripemporium.weather.ui.screens.LocationDetailScreen
import com.dripemporium.weather.ui.screens.LocationListScreen
import com.dripemporium.weather.ui.theme.WeatherAppTheme
import com.dripemporium.weather.ui.viewmodels.HistWeatherViewModel
import com.dripemporium.weather.ui.viewmodels.LocationDetailViewModel
import com.dripemporium.weather.ui.viewmodels.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // private val viewModel: WeatherComViewModel by viewModels()

    private val viewModel: LocationViewModel by viewModels()
    private val locationDetailViewModel: LocationDetailViewModel by viewModels()
    private val histWeatherViewModel: HistWeatherViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // viewModel.fetchWeather(location = "Nairobi", apiKey = "cf4c8f9b677d4bf997362513240412")
        viewModel.fetchLocations(query = "Nairobi")

        setContent {
            MaterialTheme {
                WeatherAppSet(
                    viewModel = viewModel,
                    locationDetailViewModel = locationDetailViewModel,
                    histWeatherViewModel = histWeatherViewModel
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherAppSet(
    viewModel: LocationViewModel,
    locationDetailViewModel: LocationDetailViewModel,
    histWeatherViewModel: HistWeatherViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "location_list") {
        composable("location_list") { LocationListScreen(navController, viewModel) { date ->
            onDateSelected(date, navController)
        }
        }
        composable(
            "location_details/{locationId}",
            arguments = listOf(navArgument("locationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val locationId = backStackEntry.arguments?.getString("locationId") ?: ""
            LocationDetailScreen(navController, locationId, locationDetailViewModel)
        }
        composable("history/{date}") { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: ""
            val historicalData = histWeatherViewModel.historyData.collectAsState()
            LaunchedEffect(date) {
                histWeatherViewModel.fetchHistoricalWeather(date, "Nairobi", "cf4c8f9b677d4bf997362513240412")
            }
            HistoryScreen(
                historicalData = historicalData.value?.forecast?.forecastday?.get(0)?.hour ?: emptyList(),
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

fun onDateSelected(selectedDate: Any, navController: NavController) {
    Log.d("Mkuu", "Hello ${selectedDate}")
    navController.navigate("history/$selectedDate")
}