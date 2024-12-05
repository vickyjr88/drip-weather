package com.dripemporium.weather.data

data class WeatherComResponse(
    val forecasts: List<Forecast>
)

data class Forecast(
    val dayOfWeek: String,
    val temperatureMax: Int,
    val temperatureMin: Int,
    val narrative: String
)

data class WeatherResponse(
    val location: Location,
    val current: Current
)

data class Current(
    val temp_c: Double,
    val temp_f: Double,
    val condition: Condition
)

data class Condition(
    val text: String,
    val icon: String,
    val code: Int
)

data class LocationResponse(
    val id: String,
    val name: String,
    val region: String,
    val country: String
)

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tz_id: String,
    val localtime_epoch: Int,
    val localtime: String
)

data class LocationDetailsResponse(
    val location: Location
)

data class ForecastHour(
    val forecastday: List<ForecastDay>,
)

data class ForecastDay(
    val date: String,
    val hour: List<WeatherData>
)
data class HistoryResponse(
    val forecast: ForecastHour
)
data class WeatherData(
    val date: String,          // Date of the weather observation (e.g., "2024-12-05")
    val temperature: Float,    // Temperature in Celsius
    val humidity: Int,         // Humidity percentage
    val windSpeed: Float,      // Wind speed in km/h
    val condition: Condition,      // Weather condition description (e.g., "Sunny")
    val time: String
)