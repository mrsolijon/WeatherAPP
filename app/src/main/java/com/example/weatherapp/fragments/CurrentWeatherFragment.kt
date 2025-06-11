package com.example.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.adapter.HourlyForecastRvAdapter
import com.example.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.example.weatherapp.model.HourlyForecastData
import com.example.weatherapp.service.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {

    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!

    val hourlyForecast = listOf<HourlyForecastData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCurrentWeatherBinding.bind(view)

        val adapter = HourlyForecastRvAdapter(hourlyForecast)
        binding.hourlyForecastRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.hourlyForecastRv.adapter = adapter

        val args = arguments
        if (args != null) {
            val lat = args.getDouble("lat",40.691)
            val lon = args.getDouble("lon",71.9284)
            fetchWeatherData(lat,lon)
        }
        else{

//            71.9284  40.691
            fetchWeatherData(40.691, 71.9284)
//            fetchWeatherData(41.2995, 69.2401)
        }



        binding.btnDailyForecast.setOnClickListener {
            findNavController().navigate(R.id.action_currentWeatherFragment_to_dailyForecastFragment)
        }
        binding.addCityButton.setOnClickListener {
            findNavController().navigate(R.id.action_currentWeatherFragment_to_addChangeCityFragment)
        }
        binding.currentCity.setOnClickListener{
            findNavController().navigate(R.id.action_currentWeatherFragment_to_addChangeCityFragment)
        }
        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_currentWeatherFragment_to_settingsFragment)
        }

    }

    private fun fetchWeatherData(lat: Double, lon: Double) {


        val apiKey = "42d2e302506a7c6c672dd39605397dee"

        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.getWeather(lat, lon, apiKey)
                withContext(Dispatchers.Main) {
                    // Joriy ob-havo ma’lumotlarini yangilash
                    binding.currentCity.text = "Tashkent"
                    binding.statusWeather.text = response.current.weather[0].description
                    binding.currentTemp.text = "${response.current.temp.toInt()}°"
                    binding.currentHumidity.text = "${response.current.humidity}%"
                    binding.currentWind.text = "${response.current.wind_speed} km/h"

                    // Ikonni sozlash
                    when (response.current.weather[0].icon) {
                        "01d" -> binding.statusIcon.setImageResource(R.drawable.sun)
                        else -> binding.statusIcon.setImageResource(R.drawable.cloudy)
                    }

                    // Soatlik bashorat RecyclerView
                    val hourlyAdapter = HourlyForecastRvAdapter(response.hourly.take(24))
                    binding.hourlyForecastRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    binding.hourlyForecastRv.adapter = hourlyAdapter
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Xato: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}