package com.example.weatherapp.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.adapter.HourlyForecastRvAdapter
import com.example.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.example.weatherapp.viewmodel.LocationViewModel
import com.example.weatherapp.viewmodel.WeatherData
import com.example.weatherapp.viewmodel.WeatherViewModel

class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {

    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!

    private lateinit var weatherViewModel: WeatherViewModel
    private val locationViewModel: LocationViewModel by activityViewModels()

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            locationViewModel.fetchLocation(requireContext())
        } else {
            Toast.makeText(requireContext(), "Lokatsiyaga ruxsat berilmadi", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCurrentWeatherBinding.bind(view)
        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        checkLocationPermission()
        observeLocationViewModel()
        observeWeatherViewModel()

        binding.refreshButton.setOnClickListener {
            checkLocationPermission()
            observeWeatherViewModel()
        }

        binding.currentCity.setOnClickListener {
            findNavController().navigate(R.id.action_currentWeatherFragment_to_addChangeCityFragment)
        }
        binding.addCityButton.setOnClickListener {
            findNavController().navigate(R.id.action_currentWeatherFragment_to_addChangeCityFragment)
        }
        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_currentWeatherFragment_to_settingsFragment)
        }
        binding.btnDailyForecast.setOnClickListener {
            findNavController().navigate(R.id.action_currentWeatherFragment_to_dailyForecastFragment)
        }
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                locationViewModel.fetchLocation(requireContext())
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                Toast.makeText(requireContext(), "Lokatsiya kerak: iltimos ruxsat bering", Toast.LENGTH_LONG).show()
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            else -> {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun observeLocationViewModel() {
        locationViewModel.locationData.observe(viewLifecycleOwner) { info ->
            when {
                info.isLoading -> {
                    binding.currentCity.text = "Yuklanmoqda..."
                }
                info.error != null -> {
                    binding.currentCity.text = info.error
                }
                info.latitude != null && info.longitude != null -> {
                    binding.currentCity.text = info.city
                    weatherViewModel.loadWeatherData(info.latitude, info.longitude)
                }
            }
        }
    }

    private fun observeWeatherViewModel() {
        weatherViewModel.uiWeatherData.observe(viewLifecycleOwner) { weather ->
            weather?.let { updateUI(it) }
        }

        weatherViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateUI(weather: WeatherData) {

        val (iconRes, status) = getWeatherUI(weather.icon)
        binding.statusIcon.setImageResource(iconRes)
        binding.currentTemp.text = weather.temperature
        binding.currentHumidity.text = weather.humidity
        binding.currentWind.text = weather.windSpeed
        binding.statusWeather.text = status
        val hourlyAdapter = HourlyForecastRvAdapter(weather.hourly.take(24))
        binding.hourlyForecastRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.hourlyForecastRv.adapter = hourlyAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun getWeatherUI(icon: String): Pair<Int, String> {
            return when (icon.lowercase()) {
                "01d" -> R.drawable.sun to "Quyoshli ochiq havo"
                "01n" -> R.drawable.moon to "Ochiq havo"
                "02d" -> R.drawable.cloudy_sunny to "Bulutli Quyoshli"
                "02n" -> R.drawable.cloudy_moon to "Bulutli Tun"
                "03d", "03n" -> R.drawable.cloud to "Biroz Bulutli"
                "04d", "04n" -> R.drawable.cloudy to "Bulutli"
                "09d", "09n" -> R.drawable.rain to "Kuchli Yomg'ir"
                "10d" -> R.drawable.rainy_sun to "Yomg'ir"
                "10n" -> R.drawable.rainy_night to "Yomg'ir"
                "11d", "11n" -> R.drawable.thunderstorm to "Bo'ron"
                "13d", "13n" -> R.drawable.snow to "Qor"
                else -> R.drawable.cloudy to "Bulutli"
            }
        }
    }
}
