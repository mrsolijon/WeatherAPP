package com.example.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.BuildConfig
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.adapter.DailyForecastRvAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentDailyForecastBinding
import com.example.weatherapp.service.ApiClient
import com.example.weatherapp.viewmodel.LocationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.getValue

class DailyForecastFragment : Fragment(R.layout.fragment_daily_forecast) {

    private var _binding: FragmentDailyForecastBinding? = null
    private val binding get() = _binding!!
    private val locationViewModel: LocationViewModel by activityViewModels()
    private val apiKey = BuildConfig.WEATHER_API_KEY

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDailyForecastBinding.bind(view)
        val lat = locationViewModel.locationData.value?.latitude
        val lon = locationViewModel.locationData.value?.longitude
        fetchDailyForecast(lat,lon)

        binding.dailyForecastBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun fetchDailyForecast(lat: Double?, lon: Double?) {
        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.getWeather(lat, lon, apiKey)
                withContext(Dispatchers.Main) {
                    val dailyAdapter = DailyForecastRvAdapter(response.daily)
                    binding.dailyForecastRV.layoutManager = LinearLayoutManager(context)
                    binding.dailyForecastRV.adapter = dailyAdapter
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

