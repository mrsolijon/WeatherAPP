package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.HourlyItemBinding
import com.example.weatherapp.model.hourlyForecast

class HourlyForecastRvAdapter (private val list: List<hourlyForecast>): RecyclerView.Adapter<HourlyForecastRvAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding: HourlyItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(item: hourlyForecast){
            binding.forecastHour.text = item.time
            binding.hourWeatherIcon.setImageResource(getIconForWeather(item.picPath))
            binding.hourWeatherTemp.text = "${item.temp}°"
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
            "clear", "sunny" -> R.drawable.sun
            "cloudy" -> R.drawable.cloudy
            "rain" -> R.drawable.rainy
            else -> R.drawable.sun
        }

    }
}