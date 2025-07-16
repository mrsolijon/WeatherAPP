package uz.mrsolijon.weatherapp.model.mapper

import android.app.Application
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.model.WeatherData
import uz.mrsolijon.weatherapp.model.WeatherResponse

class WeatherDataMapper(private val application: Application) {
    fun mapResponseToUiData(response: WeatherResponse): WeatherData{
        return WeatherData(
            temperature = "${response.current.temp.toInt()}C°",
            weatherStatus = response.current.weather.firstOrNull()?.description ?: application.getString(R.string.unknown),
            humidity = "${response.current.humidity}%",
            windSpeed = "${response.current.wind_speed} m/s",
            maxTemp = "${response.daily.firstOrNull()?.temp?.max?.toInt()}",
            minTemp = "${response.daily.firstOrNull()?.temp?.min?.toInt()}",
            hourly = response.hourly,
            daily = response.daily,
            icon = response.current.weather.firstOrNull()?.icon ?: "01d"
        )
    }
}