package uz.mrsolijon.weatherapp.ui.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.data.remote.model.LocationInfo
import java.util.Locale

@Suppress("DEPRECATION")
class LocationViewModel : ViewModel() {

    private val _locationData = MutableStateFlow(LocationInfo(isLoading = false))
    val locationData: StateFlow<LocationInfo> get() = _locationData

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    @SuppressLint("MissingPermission")
    fun fetchLocation(context: Context) {

        if (_locationData.value.isManuallySelected && _locationData.value.latitude != null && _locationData.value.longitude != null) {
            return
        }

        if (_locationData.value.isLoading) return

        _locationData.value = LocationInfo(isLoading = true)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 3000
        ).setMaxUpdates(1).build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    val city = getCityName(context, location.latitude, location.longitude)
                    _locationData.value = LocationInfo(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        city = city,
                        isLoading = false,
                        isManuallySelected = false
                    )
                } ?: run {
                    _locationData.value = LocationInfo(
                        isLoading = false,
                        error = context.getString(R.string.location_not_found)
                    )
                }
                fusedLocationClient.removeLocationUpdates(this)
            }

            override fun onLocationAvailability(availability: LocationAvailability) {
                if (!availability.isLocationAvailable) {
                    _locationData.value = LocationInfo(
                        isLoading = false,
                        error = context.getString(R.string.location_not_found)
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
        _locationData.value = LocationInfo(
            latitude = lat,
            longitude = lon,
            city = city,
            isLoading = false,
            isManuallySelected = true
        )
    }
}