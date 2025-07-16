package uz.mrsolijon.weatherapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.databinding.ItemDailyBinding
import uz.mrsolijon.weatherapp.fragments.CurrentWeatherFragment.Companion.getWeatherUI
import uz.mrsolijon.weatherapp.model.DailyForecastData

class DailyForecastRvAdapter(
    private val context: Context,
    private val dailyList: List<DailyForecastData>
) :
    RecyclerView.Adapter<DailyForecastRvAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemDailyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: DailyForecastData) {
            val (iconRes, status) = getWeatherUI(context, item.weather[0].icon)
            binding.forecastDay.text = getDayName(context, item.dt)
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
        return dailyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(dailyList[position])
    }

    fun getDayName(context: Context, timestamp: Long): String {
        val sdf = java.text.SimpleDateFormat("EEEE", java.util.Locale.ENGLISH)
        val date = java.util.Date(timestamp * 1000)
        val name = when (sdf.format(date)) {
            "Monday" -> context.getString(R.string.monday)
            "Tuesday" -> context.getString(R.string.tuesday)
            "Wednesday" -> context.getString(R.string.wednesday)
            "Thursday" -> context.getString(R.string.thursday)
            "Friday" -> context.getString(R.string.friday)
            "Saturday" -> context.getString(R.string.saturday)
            "Sunday" -> context.getString(R.string.sunday)
            else -> {
                context.getString(R.string.noday)
            }
        }

        return name
    }


}