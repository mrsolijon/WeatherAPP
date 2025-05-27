package com.example.weatherapp.model

data class DailyForecastData(
    val dt: Long,
    val weather: List<WeatherCondition>,
    val temp: Temperature
)
