package uz.mrsolijon.weatherapp.data.remote.model

data class DayTemperature(
    val day: Float,
    val min: Float,
    val max: Float,
    val night: Float,
    val eve: Float,
    val morn: Float
)