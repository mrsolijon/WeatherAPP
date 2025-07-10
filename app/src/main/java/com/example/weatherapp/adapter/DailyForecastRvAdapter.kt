package com.example.weatherapp.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ItemDailyBinding
import com.example.weatherapp.fragments.CurrentWeatherFragment.Companion.getWeatherUI
import com.example.weatherapp.model.DailyForecastData

class DailyForecastRvAdapter (
    private val dailylist: List<DailyForecastData>):
    RecyclerView.Adapter<DailyForecastRvAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding: ItemDailyBinding): RecyclerView.ViewHolder(binding.root){

        fun onBind(item: DailyForecastData){
            val (iconRes,status) = getWeatherUI(item.weather[0].icon)
            binding.forecastDay.text = getDayName(item.dt)
            binding.dayWeatherIcon.setImageResource(iconRes)
            @SuppressLint("SetTextI18n")
            binding.dayWeatherTemp.text = "${item.temp.day.toInt()}°"
            binding.dayWeatherStatus.text = status
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemDailyBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view)
    }



    override fun getItemCount(): Int {
        return dailylist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(dailylist[position])
    }
    fun getDayName(timestamp: Long): String {
        val sdf = java.text.SimpleDateFormat("EEEE", java.util.Locale.ENGLISH)
        val date = java.util.Date(timestamp * 1000)
        val name = when(sdf.format(date)){
            "Monday"->"Dushanba"
            "Tuesday"->"Seshanba"
            "Wednesday"->"Chorshanba"
            "Thursday"->"Payshanba"
            "Friday"->"Juma"
            "Saturday"->"Shanba"
            "Sunday"->"Yakshanba"
            else -> {"Kun aniqlanmadi"}
        }

        return name
    }





}