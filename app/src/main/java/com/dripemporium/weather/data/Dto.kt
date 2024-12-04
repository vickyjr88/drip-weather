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
    val icon: String
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