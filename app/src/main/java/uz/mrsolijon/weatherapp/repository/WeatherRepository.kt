package uz.mrsolijon.weatherapp.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import uz.mrsolijon.weatherapp.data.local.WeatherAppDatabase
import uz.mrsolijon.weatherapp.data.local.entity.WeatherEntity
import uz.mrsolijon.weatherapp.data.remote.api.ApiClient
import uz.mrsolijon.weatherapp.data.remote.model.WeatherData
import uz.mrsolijon.weatherapp.data.remote.model.WeatherResponse
import uz.mrsolijon.weatherapp.data.remote.model.mapper.WeatherDataMapper
import uz.mrsolijon.weatherapp.util.NetworkHelper
import java.time.Instant

class WeatherRepository(
    private val database: WeatherAppDatabase,
    private val networkHelper: NetworkHelper,
    private val weatherDataMapper: WeatherDataMapper
) {
    private val weatherApiService = ApiClient.weatherApiService
    private val weatherDao = database.weatherDao()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getWeather(lat: Double, lon: Double, apiKey: String, cityName: String?): WeatherData {
        return if (networkHelper.isNetworkConnected()) {
            // Internet bor, API’dan ma'lumot olamiz
            val response = weatherApiService.getWeather(lat, lon, apiKey)
            val uiData = weatherDataMapper.mapResponseToUiData(response)
            Log.d("InternetBor", "getWeather: $response")

            // API’dan olingan ma'lumotlarni local database’ga saqlaymiz
            val weatherEntity = WeatherEntity(
                lat = lat,
                lon = lon,
                timezone = response.timezone,
                cityName = cityName ?: "Noma'lum",
                temperature = response.current.temp,
                weatherStatus = response.current.weather.firstOrNull()?.description ?: "Noma'lum",
                humidity = response.current.humidity,
                windSpeed = response.current.wind_speed,
                maxTemp = response.daily.firstOrNull()?.temp?.max ?: 0f,
                minTemp = response.daily.firstOrNull()?.temp?.min ?: 0f,
                icon = response.current.weather.firstOrNull()?.icon ?: "01d",
                hourly = response.hourly,
                daily = response.daily,
                timestamp = System.currentTimeMillis()
            )
            Log.d("WeatherRepository", "Saving WeatherEntity: $weatherEntity")
            weatherDao.deleteWeatherByLocation(lat, lon)
            Log.d("WeatherRepository", "Deleted old data for lat: $lat, lon: $lon")
            weatherDao.insertWeather(weatherEntity)
            Log.d("WeatherRepository", "Inserted WeatherEntity")
            uiData.copy(cityName = cityName ?: "Noma'lum")

        } else {
            // Internet yo‘q, local database’dan ma'lumot olamiz
            val cachedWeather = weatherDao.getWeatherByLocation(lat, lon)
            Log.d("InternetYoq", "getWeather: $cachedWeather")

            cachedWeather?.let {
                // Joriy vaqtni olish
                val currentTime = Instant.now().epochSecond - 3600
                // hourly ro‘yxatini joriy vaqtdan boshlab filtrlash
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
                Log.d("WeatherRepository", "Converted WeatherData: $weatherData")
                weatherData
            } ?: run {
                Log.d("WeatherRepository", "No cached data found for lat: $lat, lon: $lon")
                WeatherData()
            }
        }
    }
}