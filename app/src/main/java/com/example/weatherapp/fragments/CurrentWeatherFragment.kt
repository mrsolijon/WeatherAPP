package com.example.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.adapter.HourlyForecastRvAdapter
import com.example.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.example.weatherapp.model.hourlyForecastData

class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {

    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!


    private var API_KEY = "42d2e302506a7c6c672dd39605397dee"

    val hourlyForecast = listOf<hourlyForecastData>(
        hourlyForecastData("14:00", "sunny", 20),
        hourlyForecastData("15:00", "rain", 20),
        hourlyForecastData("16:00", "cloudy", 20),
        hourlyForecastData("17:00", "clear", 20),
        hourlyForecastData("18:00", "sunny", 20),
        hourlyForecastData("19:00", "sunny", 20),
        hourlyForecastData("20:00", "sunny", 20),
        hourlyForecastData("21:00", "sunny", 20),
        hourlyForecastData("22:00", "sunny", 20),
        hourlyForecastData("23:00", "sunny", 20),
        hourlyForecastData("00:00", "sunny", 20),
        hourlyForecastData("01:00", "sunny", 20),
        hourlyForecastData("02:00", "sunny", 20),
        hourlyForecastData("03:00", "sunny", 20),
        hourlyForecastData("04:00", "sunny", 20),
        hourlyForecastData("05:00", "sunny", 20),
        hourlyForecastData("06:00", "sunny", 20),
        hourlyForecastData("07:00", "sunny", 20),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCurrentWeatherBinding.bind(view)

        val adapter = HourlyForecastRvAdapter(hourlyForecast)
        binding.hourlyForecastRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.hourlyForecastRv.adapter = adapter

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}