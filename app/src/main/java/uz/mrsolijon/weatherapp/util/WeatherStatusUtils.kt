package uz.mrsolijon.weatherapp.util

import android.content.Context
import uz.mrsolijon.weatherapp.R

object WeatherStatusUtils {

    fun getWeatherStatusIcon(condition: String): Int {
        return when (condition.lowercase()) {
            "01d" -> R.drawable.sun
            "01n" -> R.drawable.moon
            "02d" -> R.drawable.cloudy_sunny
            "02n" -> R.drawable.cloudy_moon
            "03d", "03n" -> R.drawable.cloud
            "04d", "04n" -> R.drawable.cloudy
            "09d", "09n" -> R.drawable.rain
            "10d" -> R.drawable.rainy_sun
            "10n" -> R.drawable.rainy_night
            "11d", "11n" -> R.drawable.thunderstorm
            "13d", "13n" -> R.drawable.snow
            "50d", "50n" -> R.drawable.mist
            else -> R.drawable.sun
        }
    }

    fun getWeatherStatus(context: Context, icon: String): String {
        return when (icon.lowercase()) {
            "01d" -> context.getString(R.string.sun_clear_sky)
            "01n" -> context.getString(R.string.moon_clear_sky)
            "02d" -> context.getString(R.string.cloudy_sunny)
            "02n" -> context.getString(R.string.cloudy_moon)
            "03d", "03n" -> context.getString(R.string.few_clouds)
            "04d", "04n" -> context.getString(R.string.cloudy)
            "09d", "09n" -> context.getString(R.string.rain)
            "10d" -> context.getString(R.string.rainy_sun)
            "10n" -> context.getString(R.string.rainy_night)
            "11d", "11n" -> context.getString(R.string.thunderstorm)
            "13d", "13n" -> context.getString(R.string.snow)
            else -> context.getString(R.string.cloudy)
        }
    }
}