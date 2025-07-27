package uz.mrsolijon.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import uz.mrsolijon.weatherapp.data.remote.model.DailyForecastData
import uz.mrsolijon.weatherapp.data.remote.model.HourlyForecastData
import uz.mrsolijon.weatherapp.util.Converters

@Entity(tableName = "weather_data")
@TypeConverters(Converters::class)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val cityName: String,
    val temperature: Float,
    val weatherStatus: String,
    val humidity: Float,
    val windSpeed: Float,
    val maxTemp: Float,
    val minTemp: Float,
    val icon: String,
    val hourly: List<HourlyForecastData>,
    val daily: List<DailyForecastData>,
    val timestamp: Long // Ma'lumot saqlangan vaqtni kuzatish uchun
)