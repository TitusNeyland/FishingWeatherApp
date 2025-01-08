package com.example.bestdaytofish.data

data class WeatherResponse(
    val list: List<DailyWeather>,
    val city: City
)

data class City(
    val name: String,
    val country: String
)

data class DailyWeather(
    val dt: Long,
    val main: Temperature,
    val weather: List<Weather>,
    val wind: Wind,
)

data class Temperature(
    val temp: Double,
    val temp_min: Double,
    val temp_max: Double,
    val humidity: Int
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double
) 