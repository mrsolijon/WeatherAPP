package uz.mrsolijon.weatherapp.data.remote.model

data class WeatherResponse(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val current: CurrentWeather,
    val hourly: List<HourlyForecastData>,
    val daily: List<DailyForecastData>
)