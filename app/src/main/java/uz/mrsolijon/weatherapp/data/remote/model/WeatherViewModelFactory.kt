package uz.mrsolijon.weatherapp.data.remote.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.mrsolijon.weatherapp.data.remote.model.mapper.WeatherDataMapper
import uz.mrsolijon.weatherapp.ui.viewmodel.WeatherViewModel

class WeatherViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            val weatherDataMapper = WeatherDataMapper(application)
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(weatherDataMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}