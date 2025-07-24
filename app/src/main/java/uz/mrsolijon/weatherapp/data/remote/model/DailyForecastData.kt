package uz.mrsolijon.weatherapp.data.remote.model

data class DailyForecastData(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val wind_speed: Float,
    val humidity: Float,
    val weather: List<WeatherCondition>,
    val temp: DayTemperature,
    var isExpanded: Boolean = false
)