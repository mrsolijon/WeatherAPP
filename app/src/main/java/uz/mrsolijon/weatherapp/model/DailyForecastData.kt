package uz.mrsolijon.weatherapp.model

data class DailyForecastData(
    val dt: Long,
    val wind_speed: Float,
    val humidity: Float,
    val weather: List<WeatherCondition>,
    val temp: DayTemperature
)