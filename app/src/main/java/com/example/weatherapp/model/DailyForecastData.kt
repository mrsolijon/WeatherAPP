package com.example.weatherapp.model

data class DailyForecastData(
    val dt: Long,
    val timezone_offset: Int,
    val weather: List<WeatherCondition>,
    val temp: DayTemperature
)
