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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dripemporium.weather.ui.screens.LocationDetailScreen
import com.dripemporium.weather.ui.screens.LocationListScreen
import com.dripemporium.weather.ui.theme.WeatherAppTheme
import com.dripemporium.weather.ui.viewmodels.LocationDetailViewModel
import com.dripemporium.weather.ui.viewmodels.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // private val viewModel: WeatherComViewModel by viewModels()

    private val viewModel: LocationViewModel by viewModels()
    private val locationDetailViewModel: LocationDetailViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // viewModel.fetchWeather(location = "Nairobi", apiKey = "cf4c8f9b677d4bf997362513240412")
        viewModel.fetchLocations(query = "Nairobi")

        setContent {
            MaterialTheme {
                WeatherAppSet(viewModel = viewModel, locationDetailViewModel = locationDetailViewModel)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherAppSet(viewModel: LocationViewModel, locationDetailViewModel: LocationDetailViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "location_list") {
        composable("location_list") { LocationListScreen(navController, viewModel) { date ->
            onDateSelected(date)
        }
        }
        composable(
            "location_details/{locationId}",
            arguments = listOf(navArgument("locationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val locationId = backStackEntry.arguments?.getString("locationId") ?: ""
            LocationDetailScreen(navController, locationId, locationDetailViewModel)
        }
    }
}

fun onDateSelected(date: Any) {
    Log.d("Mkuu", "Hello ${date}")
}