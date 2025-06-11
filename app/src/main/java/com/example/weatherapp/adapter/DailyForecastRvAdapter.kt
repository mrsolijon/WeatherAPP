package com.example.weatherapp.adapter

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.DailyItemBinding
import com.example.weatherapp.model.DailyForecastData

class DailyForecastRvAdapter (private val list: List<DailyForecastData>): RecyclerView.Adapter<DailyForecastRvAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding: DailyItemBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: DailyForecastData){
            binding.forecastDay.text = java.text.SimpleDateFormat("EEEE").format(java.util.Date(item.dt * 1000))
            binding.dayWeatherIcon.setImageResource(getIconForWeather(item.weather[0].icon))
            binding.dayWeatherTemp.text = "${item.temp.day.toInt()}°"
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DailyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

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