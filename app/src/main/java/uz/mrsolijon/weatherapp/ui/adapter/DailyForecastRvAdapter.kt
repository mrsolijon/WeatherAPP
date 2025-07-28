package uz.mrsolijon.weatherapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.data.remote.model.DailyForecastData
import uz.mrsolijon.weatherapp.databinding.ItemDailyBinding
import uz.mrsolijon.weatherapp.util.WeatherStatusUtils.getWeatherStatus
import uz.mrsolijon.weatherapp.util.WeatherStatusUtils.getWeatherStatusIcon
import java.text.DateFormat
import java.util.Date
import java.util.Locale

class DailyForecastRvAdapter(
    private val context: Context, private val dailyList: List<DailyForecastData>
) : RecyclerView.Adapter<DailyForecastRvAdapter.ViewHolder>() {
    private var firstInit = true

    inner class ViewHolder(private val binding: ItemDailyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            if (dailyList.isNotEmpty() && firstInit) {
                dailyList[0].isExpanded = true
                firstInit = false
            }

            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = dailyList[position]
                    val previouslyExpandedItem = dailyList.find { it.isExpanded }
                    if (previouslyExpandedItem != null && previouslyExpandedItem != clickedItem) {
                        previouslyExpandedItem.isExpanded = false
                        notifyItemChanged(dailyList.indexOf(previouslyExpandedItem))
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

        @SuppressLint("SetTextI18n")
        fun onBind(item: DailyForecastData) {
            val iconRes = getWeatherStatusIcon(item.weather[0].icon)
            val status = getWeatherStatus(context, item.weather[0].icon)
            val dayName = getDayName(context, item.dt, adapterPosition)
            if (!item.isExpanded) {
                binding.isNotExpandedDailyDetailsLayout.visibility = View.VISIBLE
                binding.apply {
                    forecastDay.text = dayName
                    dayWeatherIcon.setImageResource(iconRes)
                    dayWeatherTemp.text = "${item.temp.max.toInt()}°"
                    dayWeatherStatus.text = status
                    isExpandedDailyDetailsLayout.visibility = View.GONE
                }

            } else {
                binding.isExpandedDailyDetailsLayout.visibility = View.VISIBLE

                binding.apply {
                    isNotExpandedDailyDetailsLayout.visibility = View.GONE
                    expandedDailyForecastTime.text = dayName
                    expandedDailyForecastStatusIcon.setImageResource(iconRes)
                    expandedDailyForecastStatusWeather.text = status
                    expandedDailyForecastTemp.text = "${item.temp.day.toInt()}°"
                    expandedDailyForecastSunriseTime.text =
                        DateFormat.getTimeInstance(DateFormat.SHORT).format(item.sunrise * 1000)
                    expandedDailyForecastSunsetTime.text =
                        DateFormat.getTimeInstance(DateFormat.SHORT).format(item.sunset * 1000)
                    expandedDailyForecastHumidity.text = "${item.humidity.toInt()} %"
                    expandedDailyForecastWind.text = "${item.wind_speed} m/s"
                    expandedDailyForecastNightTemp.text = "${item.temp.night.toInt()}°"
                    expandedDailyForecastMornTemp.text = "${item.temp.morn.toInt()}°"
                    expandedDailyForecastDayTemp.text = "${item.temp.day.toInt()}°"
                    expandedDailyForecastEveTemp.text = "${item.temp.eve.toInt()}°"
                }
            }

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

    fun getDayName(context: Context, timestamp: Long, position: Int): String {
        val sdf = DateFormat.getDateInstance(DateFormat.FULL, Locale.ENGLISH)
        val date = Date(timestamp * 1000)
        return when {
            position == 0 -> context.getString(R.string.today)
            position == 1 -> context.getString(R.string.tomorrow)
            else -> {
                when (sdf.format(date).substringBefore(",")) {
                    "Monday" -> context.getString(R.string.monday)
                    "Tuesday" -> context.getString(R.string.tuesday)
                    "Wednesday" -> context.getString(R.string.wednesday)
                    "Thursday" -> context.getString(R.string.thursday)
                    "Friday" -> context.getString(R.string.friday)
                    "Saturday" -> context.getString(R.string.saturday)
                    "Sunday" -> context.getString(R.string.sunday)
                    else -> context.getString(R.string.noday)

                }
            }
        }

    }


}