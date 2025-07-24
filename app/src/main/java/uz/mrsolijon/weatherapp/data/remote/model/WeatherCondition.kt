package uz.mrsolijon.weatherapp.data.remote.model

data class WeatherCondition(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)