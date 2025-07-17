package uz.mrsolijon.weatherapp.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.adapter.HourlyForecastRvAdapter
import uz.mrsolijon.weatherapp.databinding.FragmentCurrentWeatherBinding
import uz.mrsolijon.weatherapp.model.WeatherData
import uz.mrsolijon.weatherapp.model.WeatherViewModelFactory
import uz.mrsolijon.weatherapp.viewmodels.LocationViewModel
import uz.mrsolijon.weatherapp.viewmodels.WeatherViewModel
import kotlin.getValue

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
            Toast.makeText(
                requireContext(),
                R.string.location_access_denied.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCurrentWeatherBinding.bind(view)

        val factory = WeatherViewModelFactory(requireActivity().application)
        weatherViewModel = ViewModelProvider(requireActivity(), factory)[WeatherViewModel::class.java]
        if (locationViewModel.locationData.value.isManuallySelected == false || locationViewModel.locationData.value.latitude == null) {
            checkLocationPermission()
        } else {
            observeViewModels()
        }
        setupListeners()
        observeViewModels()
    }

    private fun setupListeners() {
        binding.refreshButton.setOnClickListener {
            locationViewModel.locationData.value.let { info ->
                if (info.isManuallySelected && info.latitude != null && info.longitude != null) {
                    weatherViewModel.loadWeatherData(info.latitude, info.longitude, info.city)
                } else {
                    checkLocationPermission()
                }
            }
        }
        binding.refreshLocation.setOnClickListener {
            locationViewModel.locationData.value.isManuallySelected = false
            locationViewModel.fetchLocation(requireContext())
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
                Toast.makeText(
                    requireContext(),
                    context?.getString(R.string.must_location),
                    Toast.LENGTH_LONG
                ).show()
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            else -> {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun observeViewModels() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    locationViewModel.locationData.collectLatest { info ->
                        when {
                            info.isLoading -> {
                                binding.currentCity.text = getString(R.string.loading)
                            }

                            info.error != null -> {
                                binding.currentCity.text = info.error
                            }

                            info.latitude != null && info.longitude != null -> {
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
        val (iconRes, status) = getWeatherUI(requireContext(), weather.icon.toString())
        binding.statusIcon.setImageResource(iconRes)
        binding.currentTemp.text = weather.temperature
        binding.currentHumidity.text = weather.humidity
        binding.currentWind.text = weather.windSpeed
        binding.statusWeather.text = status
        val hourlyAdapter = HourlyForecastRvAdapter(weather.hourly.take(24))
        binding.hourlyForecastRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.hourlyForecastRv.adapter = hourlyAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun getWeatherUI(context: Context, icon: String): Pair<Int, String> {
            return when (icon.lowercase()) {
                "01d" -> R.drawable.sun to context.getString(R.string.sun_clear_sky)
                "01n" -> R.drawable.moon to context.getString(R.string.moon_clear_sky)
                "02d" -> R.drawable.cloudy_sunny to context.getString(R.string.cloudy_sunny)
                "02n" -> R.drawable.cloudy_moon to context.getString(R.string.cloudy_moon)
                "03d", "03n" -> R.drawable.cloud to context.getString(R.string.few_clouds)
                "04d", "04n" -> R.drawable.cloudy to context.getString(R.string.cloudy)
                "09d", "09n" -> R.drawable.rain to context.getString(R.string.rain)
                "10d" -> R.drawable.rainy_sun to context.getString(R.string.rainy_sun)
                "10n" -> R.drawable.rainy_night to context.getString(R.string.rainy_night)
                "11d", "11n" -> R.drawable.thunderstorm to context.getString(R.string.thunderstorm)
                "13d", "13n" -> R.drawable.snow to context.getString(R.string.snow)
                else -> R.drawable.cloudy to context.getString(R.string.cloudy)
            }
        }
    }
}