package uz.mrsolijon.weatherapp.repository

import android.app.Application
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.data.local.WeatherAppDatabase
import uz.mrsolijon.weatherapp.data.local.entity.WeatherEntity
import uz.mrsolijon.weatherapp.data.remote.api.WeatherApiService
import uz.mrsolijon.weatherapp.data.remote.model.WeatherData
import uz.mrsolijon.weatherapp.data.remote.model.mapper.WeatherDataMapper
import uz.mrsolijon.weatherapp.util.NetworkHelper
import java.time.Instant
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val database: WeatherAppDatabase,
    private val networkHelper: NetworkHelper,
    private val weatherDataMapper: WeatherDataMapper,
    private val weatherApiService: WeatherApiService,
    private val application: Application
) {
    private val weatherDao = database.weatherDao()

    suspend fun getWeather(
        lat: Double,
        lon: Double,
        apiKey: String,
        cityName: String?
    ): WeatherData {

        return if (networkHelper.isNetworkConnected()) {

            // API’dan ma'lumot olish
            val response = weatherApiService.getWeather(lat, lon, apiKey)
            val uiData = weatherDataMapper.mapResponseToUiData(response)

            // API’dan olingan ma'lumotlarni local database’ga saqlash
            val weatherEntity = WeatherEntity(
                lat = lat,
                lon = lon,
                timezone = response.timezone,
                cityName = cityName ?: application.getString(R.string.unknown),

                temperature = response.current.temp,
                weatherStatus = response.current.weather.firstOrNull()?.description
                    ?: application.getString(R.string.unknown),
                humidity = response.current.humidity,
                windSpeed = response.current.wind_speed,
                maxTemp = response.daily.firstOrNull()?.temp?.max ?: 0f,
                minTemp = response.daily.firstOrNull()?.temp?.min ?: 0f,
                icon = response.current.weather.firstOrNull()?.icon ?: "01d",
                hourly = response.hourly,
                daily = response.daily,
                timestamp = System.currentTimeMillis()
            )
            weatherDao.deleteWeatherByLocation(lat, lon)
            weatherDao.insertWeather(weatherEntity)
            uiData.copy(cityName = cityName ?: application.getString(R.string.unknown))

        } else {

            // Internet yo‘q, local database’dan ma'lumot olish
            val cachedWeather = weatherDao.getWeatherByLocation(lat, lon)

            cachedWeather?.let {
                val currentTime = Instant.now().epochSecond - 3600
                val filteredHourly = it.hourly.filter { hourlyData ->
                    hourlyData.dt >= currentTime
                }


                val weatherData = WeatherData(
                    cityName = it.cityName,
                    temperature = "${it.temperature.toInt()}°C",
                    weatherStatus = it.weatherStatus,
                    humidity = "${it.humidity}%",
                    windSpeed = "${it.windSpeed} m/s",
                    maxTemp = "${it.maxTemp.toInt()}°C",
                    minTemp = "${it.minTemp.toInt()}°C",
                    hourly = filteredHourly,
                    daily = it.daily,
                    icon = it.icon
                )

                weatherData
            } ?: run {
                WeatherData()
            }
        }
    }
}