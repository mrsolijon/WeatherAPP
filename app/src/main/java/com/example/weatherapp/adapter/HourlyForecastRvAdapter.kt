package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.HourlyItemBinding
import com.example.weatherapp.model.HourlyForecastData

class HourlyForecastRvAdapter (private val list: List<HourlyForecastData>): RecyclerView.Adapter<HourlyForecastRvAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding: HourlyItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun onBind(item: HourlyForecastData){
            binding.forecastHour.text = java.text.SimpleDateFormat("HH:MM").format(java.util.Date(item.dt * 1000))
            binding.hourWeatherIcon.setImageResource(getIconForWeather(item.weather[0].icon))
            binding.hourWeatherTemp.text = "${item.temp.toInt()}°"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = HourlyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    fun getIconForWeather(condition: String): Int {
        return when (condition.lowercase()) {
            "01d", "01n" -> R.drawable.sun
            "02d", "02n" -> R.drawable.cloudy
            "03d", "03n", "04d", "04n" -> R.drawable.cloudy
            "09d", "09n", "10d", "10n" -> R.drawable.rainy
            else -> R.drawable.sun
        }

    }
}