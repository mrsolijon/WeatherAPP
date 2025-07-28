package uz.mrsolijon.weatherapp.data.remote.model

data class WeatherData(
    val cityName: String = "",
    val temperature: String = "--°C",
    val weatherStatus: String = "",
    val humidity: String = "--%",
    val windSpeed: String = "-- m/s",
    val maxTemp: String = "--°C",
    val minTemp: String = "--°C",
    val hourly: List<HourlyForecastData> = emptyList(),
    val daily: List<DailyForecastData> = emptyList(),
    val icon: String = "01d"
)