package com.example.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel // Agar string resurslari kabi narsalar uchun Application context kerak bo'lsa
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.DailyForecastData
import com.example.weatherapp.model.HourlyForecastData
import com.example.weatherapp.service.ApiClient
import com.example.weatherapp.model.WeatherResponse
import kotlinx.coroutines.launch
import com.example.weatherapp.BuildConfig

data class WeatherData(
    val cityName: String = "Noma'lum",
    val temperature: String = "--°C",
    val weatherStatus: String = "Noma'lum",
    val humidity: String = "--%",
    val windSpeed: String = "-- m/s",
    val maxTemp: String = "--°C",
    val minTemp: String = "--°C",
    val hourly: List<HourlyForecastData> = emptyList(),
    val daily: List<DailyForecastData> = emptyList(),
    val icon: String
)
val apiKey = BuildConfig.WEATHER_API_KEY

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val weatherApiService = ApiClient.weatherApiService

    // LiveData obyektlari
    private val _uiWeatherData = MutableLiveData<WeatherData>()
    val uiWeatherData: LiveData<WeatherData> get() = _uiWeatherData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    //   Berilgan koordinatalar uchun ob-havo ma'lumotlarini olish uchun.

    fun loadWeatherData(latitude: Double, longitude: Double) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = weatherApiService.getWeather(latitude, longitude, apiKey)
                val mappedData = mapResponseToUiData(response)
                _uiWeatherData.postValue(mappedData)
            } catch (e: Exception) {
                _errorMessage.postValue("Ob-havo ma'lumotlarini yuklashda xatolik: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun mapResponseToUiData(response: WeatherResponse): WeatherData {
        return WeatherData(
            cityName = response.timezone,
            temperature = "${response.current.temp.toInt()}C°",
            weatherStatus = response.current.weather.firstOrNull()?.description ?: "Noma'lum",
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