package uz.mrsolijon.weatherapp.model

data class CurrentWeather(
    val dt: Long,
    val temp: Float,
    val feels_like: Float,
    val humidity: Int,
    val wind_speed: Float,
    val weather: List<WeatherCondition>
)
