package uz.mrsolijon.weatherapp.data.remote.model

data class CityResponse(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String
)
