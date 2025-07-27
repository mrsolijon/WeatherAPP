package uz.mrsolijon.weatherapp.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.mrsolijon.weatherapp.data.remote.model.DailyForecastData
import uz.mrsolijon.weatherapp.data.remote.model.HourlyForecastData

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromHourlyList(hourly: List<HourlyForecastData>): String {
        return gson.toJson(hourly)
    }

    @TypeConverter
    fun toHourlyList(hourlyString: String): List<HourlyForecastData> {
        val type = object : TypeToken<List<HourlyForecastData>>() {}.type
        return gson.fromJson(hourlyString, type)
    }

    @TypeConverter
    fun fromDailyList(daily: List<DailyForecastData>): String {
        return gson.toJson(daily)
    }

    @TypeConverter
    fun toDailyList(dailyString: String): List<DailyForecastData> {
        val type = object : TypeToken<List<DailyForecastData>>() {}.type
        return gson.fromJson(dailyString, type)
    }
}