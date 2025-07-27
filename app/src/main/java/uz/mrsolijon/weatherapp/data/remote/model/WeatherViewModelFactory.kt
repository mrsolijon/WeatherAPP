package uz.mrsolijon.weatherapp.data.remote.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.mrsolijon.weatherapp.data.local.WeatherAppDatabase
import uz.mrsolijon.weatherapp.data.remote.model.mapper.WeatherDataMapper
import uz.mrsolijon.weatherapp.repository.WeatherRepository
import uz.mrsolijon.weatherapp.ui.viewmodel.WeatherViewModel
import uz.mrsolijon.weatherapp.util.NetworkHelper

class WeatherViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            val weatherDataMapper = WeatherDataMapper(application)
            val database = WeatherAppDatabase.getDatabase(application)
            val networkHelper = NetworkHelper(application)
            val repository = WeatherRepository(database, networkHelper, weatherDataMapper)
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}