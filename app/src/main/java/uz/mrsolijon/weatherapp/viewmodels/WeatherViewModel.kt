package uz.mrsolijon.weatherapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import uz.mrsolijon.weatherapp.BuildConfig
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.api.ApiClient
import uz.mrsolijon.weatherapp.model.DailyForecastData
import uz.mrsolijon.weatherapp.model.WeatherData
import uz.mrsolijon.weatherapp.model.WeatherResponse
import uz.mrsolijon.weatherapp.model.mapper.WeatherDataMapper

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    val apiKey = BuildConfig.WEATHER_API_KEY
    private val weatherApiService = ApiClient.weatherApiService
    private val weatherDataMapper = WeatherDataMapper(application)

    private val _uiWeatherData = MutableStateFlow(WeatherData())
    val uiWeatherData: StateFlow<WeatherData?> get() = _uiWeatherData.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _dailyForecastData = MutableStateFlow<List<DailyForecastData>>(emptyList())
    val dailyForecastData: StateFlow<List<DailyForecastData>> get() = _dailyForecastData.asStateFlow()

    fun loadWeatherData(latitude: Double, longitude: Double, cityName: String? = null) {
        viewModelScope.launch {
            flow {
                emit(Unit)
                _isLoading.value = true

                val response = weatherApiService.getWeather(latitude, longitude, apiKey)
                val mappedData = weatherDataMapper.mapResponseToUiData(response)

                _uiWeatherData.value = mappedData.copy(
                    cityName = cityName ?: getApplication<Application>().getString(R.string.unknown)
                )
                _dailyForecastData.value = response.daily

            }
                .catch { e ->
                    _errorMessage.value = e.message
                }
                .onCompletion {
                    _isLoading.value = false
                }
                .collect {}

        }
    }
}