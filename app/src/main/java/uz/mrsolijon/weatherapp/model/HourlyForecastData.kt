package uz.mrsolijon.weatherapp.model

data class HourlyForecastData(
    val dt: Long,
    val weather: List<WeatherCondition>,
    val temp: Float,
    val feels_like: Float,
    val humidity: Float,
    val wind_speed: Float,
    val pressure: Float,
    var isExpanded : Boolean = false
)
