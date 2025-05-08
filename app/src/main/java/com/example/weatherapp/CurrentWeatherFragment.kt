package com.example.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapter.HourlyForecastRvAdapter
import com.example.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.example.weatherapp.model.hourlyForecast


class CurrentWeatherFragment : Fragment() {

    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!

    val hourlyForecast = listOf<hourlyForecast>(
        hourlyForecast("14:00", "sunny", 20),
        hourlyForecast("15:00", "rain", 20),
        hourlyForecast("16:00", "cloudy", 20),
        hourlyForecast("17:00", "clear", 20),
        hourlyForecast("18:00", "sunny", 20),
        hourlyForecast("19:00", "sunny", 20),
        hourlyForecast("20:00", "sunny", 20),
        hourlyForecast("21:00", "sunny", 20),
        hourlyForecast("22:00", "sunny", 20),
        hourlyForecast("23:00", "sunny", 20),
        hourlyForecast("00:00", "sunny", 20),
        hourlyForecast("01:00", "sunny", 20),
        hourlyForecast("02:00", "sunny", 20),
        hourlyForecast("03:00", "sunny", 20),
        hourlyForecast("04:00", "sunny", 20),
        hourlyForecast("05:00", "sunny", 20),
        hourlyForecast("06:00", "sunny", 20),
        hourlyForecast("07:00", "sunny", 20),
    )



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        val adapter = HourlyForecastRvAdapter(hourlyForecast)
        binding.hourlyForecastRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.hourlyForecastRv.adapter = adapter

    }




}