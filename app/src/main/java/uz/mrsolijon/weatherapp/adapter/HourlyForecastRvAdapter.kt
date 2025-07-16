package uz.mrsolijon.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.databinding.ItemHourlyBinding
import uz.mrsolijon.weatherapp.model.HourlyForecastData

class HourlyForecastRvAdapter(private val hourlyList: List<HourlyForecastData>) :
    RecyclerView.Adapter<HourlyForecastRvAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemHourlyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: HourlyForecastData) {
            binding.forecastHour.text =
                java.text.SimpleDateFormat("HH:mm").format(java.util.Date(item.dt * 1000))
            binding.hourWeatherIcon.setImageResource(getIconForWeather(item.weather[0].icon))
            binding.hourWeatherTemp.text = "${item.temp.toInt()}°"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemHourlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hourlyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(hourlyList[position])
    }

    fun getIconForWeather(condition: String): Int {
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
}