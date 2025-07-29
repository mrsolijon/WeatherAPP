package uz.mrsolijon.weatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import uz.mrsolijon.weatherapp.BuildConfig
import uz.mrsolijon.weatherapp.data.remote.model.DailyForecastData
import uz.mrsolijon.weatherapp.data.remote.model.WeatherData
import uz.mrsolijon.weatherapp.repository.WeatherRepository
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val apiKey = BuildConfig.WEATHER_API_KEY

    private val _uiWeatherData = MutableStateFlow(WeatherData())
    val uiWeatherData: StateFlow<WeatherData?> get() = _uiWeatherData.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _dailyForecastData = MutableStateFlow<List<DailyForecastData>>(emptyList())
    val dailyForecastData: StateFlow<List<DailyForecastData>> get() = _dailyForecastData.asStateFlow()

    fun loadWeatherData(
        latitude: Double,
        longitude: Double,
        cityName: String? = null,
        isManuallySelected: Boolean = false
    ) {
        viewModelScope.launch {

            flow {
                emit(Unit)
                _isLoading.value = true
                val weatherData =
                    weatherRepository.getWeather(
                        latitude,
                        longitude,
                        apiKey,
                        cityName,
                        isManuallySelected
                    )
                _uiWeatherData.value = weatherData
                _dailyForecastData.value = weatherData.daily

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