package uz.mrsolijon.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import uz.mrsolijon.weatherapp.data.local.entity.WeatherEntity

@Dao
interface WeatherDao {
    @Transaction
    suspend fun updateWeather(lat: Double, lon: Double, weather: WeatherEntity) {
        deleteWeatherByLocation(lat, lon)
        insertWeather(weather)
    }
    @Insert
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather_data WHERE lat = :lat AND lon = :lon LIMIT 1")
    suspend fun getWeatherByLocation(lat: Double, lon: Double): WeatherEntity?

    @Query("DELETE FROM weather_data WHERE lat = :lat AND lon = :lon")
    suspend fun deleteWeatherByLocation(lat: Double, lon: Double)

    @Query("SELECT * FROM weather_data")
    suspend fun getAllWeather(): List<WeatherEntity>
}