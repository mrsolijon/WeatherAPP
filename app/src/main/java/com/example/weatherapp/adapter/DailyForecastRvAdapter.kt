package com.example.weatherapp.adapter

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.DailyItemBinding
import com.example.weatherapp.model.dailyForecastData

class DailyForecastRvAdapter (private val list: List<dailyForecastData>): RecyclerView.Adapter<DailyForecastRvAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding: DailyItemBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: dailyForecastData){
            binding.forecastDay.text = item.date
            binding.dayWeatherIcon.setImageResource(getIconForWeather(item.picPath))
            binding.dayWeatherTemp.text = "${item.temp}°"
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
            "clear", "sunny" -> R.drawable.sun
            "cloudy" -> R.drawable.cloudy
            "rain" -> R.drawable.rainy
            else -> R.drawable.sun
        }

    }

}