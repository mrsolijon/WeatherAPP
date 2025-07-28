package uz.mrsolijon.weatherapp.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.mrsolijon.weatherapp.BuildConfig
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.data.remote.api.GeocodingApiService
import uz.mrsolijon.weatherapp.data.remote.model.CityResponse
import uz.mrsolijon.weatherapp.data.remote.model.LocationInfo
import java.util.Locale
import javax.inject.Inject

@Suppress("DEPRECATION")
@HiltViewModel
class LocationViewModel @Inject constructor(
    private val application: Application,
    private val geocodingApiService: GeocodingApiService
) : ViewModel() {

    private val _locationData = MutableStateFlow(LocationInfo(isLoading = false))
    val locationData: StateFlow<LocationInfo> get() = _locationData

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val _searchResults = MutableStateFlow<List<CityResponse>>(emptyList())
    val searchResults: StateFlow<List<CityResponse>> = _searchResults

    private val apiKey = BuildConfig.WEATHER_API_KEY


    @SuppressLint("MissingPermission")
    fun fetchLocation() {

        if (_locationData.value.isManuallySelected && _locationData.value.latitude != null && _locationData.value.longitude != null) {
            return
        }

        if (_locationData.value.isLoading) return

        if (_locationData.value.latitude == null || _locationData.value.longitude == null) {
            _locationData.value = LocationInfo(isLoading = true)
        } else {
            return
        }

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(application.applicationContext)

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    val cityName = getCityName(
                        application.applicationContext,
                        location.latitude,
                        location.longitude
                    )
                    _locationData.value = LocationInfo(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        city = cityName,
                        isLoading = false
                    )
                } ?: run {
                    _locationData.value = LocationInfo(
                        isLoading = false,
                        error = application.getString(R.string.location_not_found)
                    )
                }
                fusedLocationClient.removeLocationUpdates(this)
            }

            override fun onLocationAvailability(availability: LocationAvailability) {
                if (!availability.isLocationAvailable) {
                    _locationData.value = LocationInfo(
                        isLoading = false,
                        error = application.getString(R.string.location_not_found)
                    )
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun getCityName(context: Context, lat: Double, lon: Double): String {
        return try {
            val geo = Geocoder(context, Locale.getDefault())
            val address = geo.getFromLocation(lat, lon, 1)
            address?.get(0)?.locality ?: context.getString(R.string.unknown_location)
        } catch (e: Exception) {
            context.getString(R.string.not_found)
        }
    }

    fun updateSelectedCity(city: String, lat: Double, lon: Double) {
        Log.d("LocationViewModel", "Updating selected city: $city")
        _locationData.value = LocationInfo(
            latitude = lat,
            longitude = lon,
            city = city,
            isLoading = false,
            isManuallySelected = true
        )
    }

    fun searchCities(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                _searchResults.value = emptyList()
                return@launch
            }
            try {
                val cities = geocodingApiService.getCitiesByName(query, limit = 5, apiKey = apiKey)
                _searchResults.value = cities
            } catch (e: Exception) {
                _searchResults.value = emptyList()
            }
        }
    }
}