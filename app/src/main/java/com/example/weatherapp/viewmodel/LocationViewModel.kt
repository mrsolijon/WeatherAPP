package com.example.weatherapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.service.ApiClient
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

@Suppress("DEPRECATION", "DEPRECATION")
class LocationViewModel : ViewModel() {

    data class LocationInfo(
        val latitude: Double? = null,
        val longitude: Double? = null,
        val city: String? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _locationData = MutableLiveData<LocationInfo>()
    val locationData: LiveData<LocationInfo> get() = _locationData

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    fun fetchLocation(context: Context) {
        _locationData.value = LocationInfo(isLoading = true)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 3000
        ).setMaxUpdates(1).build()

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    val location: Location? = result.lastLocation
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        val city = getCityName(context, latitude, longitude)
                        _locationData.postValue(
                            LocationInfo(
                                latitude = latitude,
                                longitude = longitude,
                                city = city,
                                isLoading = false
                            )
                        )
                    } else {
                        _locationData.postValue(
                            LocationInfo(
                                isLoading = false,
                                error = "Lokatsiya topilmadi"
                            )
                        )
                    }
                }

                override fun onLocationAvailability(availability: LocationAvailability) {
                    if (!availability.isLocationAvailable) {
                        _locationData.postValue(
                            LocationInfo(
                                isLoading = false,
                                error = "Lokatsiya mavjud emas"
                            )
                        )
                    }
                }
            },
            Looper.getMainLooper()
        )
    }
    fun searchCityCoordinates(cityName: String, context: Context, apiKey: String) {
        _locationData.value = LocationInfo(isLoading = true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cities = ApiClient.cityApiService.getCityCoordinates(
                    cityName = cityName,
                    apiKey = apiKey
                )
                if (cities.isNotEmpty()) {
                    val city = cities[0]
                    LocationInfo(
                        latitude = city.lat,
                        longitude = city.lon,
                        city = cityName,
                        isLoading = false
                    )
                }
                else{
                    withContext(Dispatchers.Main) {
                        _locationData.value = LocationInfo(
                            isLoading = false,
                            error = "Shahar topilmadi"
                        )
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _locationData.value = LocationInfo(
                        isLoading = false,
                        error = "${e.message}"
                    )
                }
            }
        }
    }

    private fun getCityName(context: Context, lat: Double, lon: Double): String {
        return try {
            val geo = Geocoder(context, Locale.getDefault())
            val address = geo.getFromLocation(lat, lon, 1)
            address?.get(0)?.locality ?: "Noma'lum joy"
        } catch (e: Exception) {
            LocationInfo(isLoading = false, error = "${e.message}")
        }.toString()
    }
}
