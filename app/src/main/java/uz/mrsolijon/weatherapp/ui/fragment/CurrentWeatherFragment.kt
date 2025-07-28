package uz.mrsolijon.weatherapp.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.data.remote.model.WeatherData
import uz.mrsolijon.weatherapp.databinding.FragmentCurrentWeatherBinding
import uz.mrsolijon.weatherapp.ui.adapter.HourlyForecastRvAdapter
import uz.mrsolijon.weatherapp.ui.viewmodel.LocationViewModel
import uz.mrsolijon.weatherapp.ui.viewmodel.WeatherViewModel
import uz.mrsolijon.weatherapp.util.WeatherStatusUtils.getWeatherStatus
import uz.mrsolijon.weatherapp.util.WeatherStatusUtils.getWeatherStatusIcon

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {

    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!

    private val locationViewModel: LocationViewModel by activityViewModels()
    private val weatherViewModel: WeatherViewModel by activityViewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            locationViewModel.fetchLocation()
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.location_permission_not_granted),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCurrentWeatherBinding.bind(view)

        setupListeners()
        observeViewModels()
        checkLocationPermission()
    }

    private fun setupListeners() {
        binding.refreshButton.setOnClickListener {
            locationViewModel.locationData.value.let { info ->
                if (info.latitude != null && info.longitude != null) {
                    weatherViewModel.loadWeatherData(info.latitude, info.longitude, info.city)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.information_is_being_updated),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    checkLocationPermission()
                }
            }
        }
        binding.refreshLocation.setOnClickListener {
            locationViewModel.locationData.value.isManuallySelected = false
            locationViewModel.fetchLocation()
        }
        binding.addCityButton.setOnClickListener {
            findNavController().navigate(R.id.action_currentWeatherFragment_to_addChangeCityFragment)
        }
        binding.btnDailyForecast.setOnClickListener {
            findNavController().navigate(R.id.action_currentWeatherFragment_to_dailyForecastFragment)
        }
        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_currentWeatherFragment_to_settingsFragment)
        }

    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                locationViewModel.fetchLocation()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.location_information_is_required),
                    Toast.LENGTH_LONG
                ).show()
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun observeViewModels() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    locationViewModel.locationData.collectLatest { info ->
                        Log.d(
                            "CurrentWeatherFragment",
                            "LocationInfo yangilandi: $info"
                        ) // Bu logni qo'shing
                        when {
                            info.isLoading -> {
                                binding.currentCity.text =
                                    getString(R.string.locating)
                            }

                            info.error != null -> {
                                binding.currentCity.text =
                                    getString(R.string.location_not_found)
                            }

                            (info.latitude != null && info.longitude != null) -> {
                                Log.d(
                                    "CurrentWeatherFragment",
                                    "loadWeatherData chaqirilmoqda: Lat=${info.latitude}, Lon=${info.longitude}, City=${info.city}"
                                ) // Bu logni qo'shing
                                binding.currentCity.text = info.city
                                weatherViewModel.loadWeatherData(
                                    info.latitude,
                                    info.longitude,
                                    info.city
                                )
                            }
                        }
                    }
                }

                launch {
                    weatherViewModel.uiWeatherData.collectLatest { weather ->
                        weather?.let { updateUI(it) }
                    }
                }
            }
        }
    }

    private fun updateUI(weather: WeatherData) {
        val iconRes = getWeatherStatusIcon(weather.icon)
        val status = getWeatherStatus(requireContext(), weather.icon)
        val hourlyAdapter = HourlyForecastRvAdapter(weather.hourly.take(24))

        binding.apply {
            currentCity.text = weather.cityName
            statusIcon.setImageResource(iconRes)
            currentTemp.text = weather.temperature
            currentHumidity.text = weather.humidity
            currentWind.text = weather.windSpeed
            statusWeather.text = status
            hourlyForecastRv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            hourlyForecastRv.adapter = hourlyAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}