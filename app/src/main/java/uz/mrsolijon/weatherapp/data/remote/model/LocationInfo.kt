package uz.mrsolijon.weatherapp.data.remote.model

data class LocationInfo(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val city: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    var isManuallySelected: Boolean = false
)