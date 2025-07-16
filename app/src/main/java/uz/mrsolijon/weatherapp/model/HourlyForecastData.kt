package uz.mrsolijon.weatherapp.model

data class HourlyForecastData(
    val dt: Long,
    val weather: List<WeatherCondition>,
    val temp: Float
)