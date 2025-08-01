package uz.mrsolijon.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.data.remote.model.HourlyForecastData
import uz.mrsolijon.weatherapp.databinding.ItemHourlyBinding
import uz.mrsolijon.weatherapp.util.WeatherStatusUtils.getWeatherStatusIcon
import java.text.DateFormat
import java.util.Date

class HourlyForecastRvAdapter(private val hourlyList: List<HourlyForecastData>) :
    RecyclerView.Adapter<HourlyForecastRvAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemHourlyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = hourlyList[position]
                    val previouslyExpandedItem = hourlyList.find { it.isExpanded }
                    if (previouslyExpandedItem != null && previouslyExpandedItem != clickedItem) {
                        previouslyExpandedItem.isExpanded = false
                        notifyItemChanged(hourlyList.indexOf(previouslyExpandedItem))
                    }
                    if (clickedItem.isExpanded == true) {
                        clickedItem.isExpanded = false
                        notifyItemChanged(position)
                    } else {
                        clickedItem.isExpanded = true
                        notifyItemChanged(position)
                    }
                }
            }
        }


        fun onBind(item: HourlyForecastData) {
            binding.hourlyForecastMainLayout.setBackgroundResource(R.drawable.background_forecast)
            binding.forecastHour.text =
                DateFormat.getTimeInstance(DateFormat.SHORT).format(Date(item.dt * 1000))
            binding.hourWeatherIcon.setImageResource(getWeatherStatusIcon(item.weather[0].icon))
            binding.hourWeatherTemp.text = "${item.temp.toInt()}°"
            if (item.isExpanded) {
                binding.isExpandedDetailsLayout.visibility = View.VISIBLE
                binding.hourlyForecastMainLayout.setBackgroundResource(R.drawable.background_forecast_expanded)
                binding.isNotExpandedDetailsLayout.visibility = View.GONE
                binding.expandedForecastHour.text =
                    DateFormat.getTimeInstance(DateFormat.SHORT).format(Date(item.dt * 1000))
                binding.expandedHourWeatherFeelTemp.text = "${item.feels_like.toInt()}°"
                binding.expandedHourWeatherHumidity.text = "${item.humidity}%"
                binding.expandedHourWeatherWindSpeed.text = "${item.wind_speed} m/s"
                binding.expandedHourWeatherPressure.text = "${item.pressure} hPa"
            } else {
                binding.isNotExpandedDetailsLayout.visibility = View.VISIBLE
                binding.isExpandedDetailsLayout.visibility = View.GONE

            }

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
}