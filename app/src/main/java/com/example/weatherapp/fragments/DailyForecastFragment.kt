package com.example.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.adapter.DailyForecastRvAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentDailyForecastBinding
import com.example.weatherapp.model.DailyForecastData
import com.example.weatherapp.service.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DailyForecastFragment : Fragment(R.layout.fragment_daily_forecast) {

    private var _binding: FragmentDailyForecastBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDailyForecastBinding.bind(view)

        fetchDailyForecast()

        //Ortga qaytish tugmasi
        binding.dailyForecastBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun fetchDailyForecast() {
        val lat = 41.2995
        val lon = 69.2401
        val apiKey = "42d2e302506a7c6c672dd39605397dee"

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

