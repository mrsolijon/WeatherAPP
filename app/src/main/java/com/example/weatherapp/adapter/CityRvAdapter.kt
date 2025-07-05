package com.example.weatherapp.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.CityResponse

class CityAdapter(
    private val cities: List<CityResponse>,
    private val onCitySelected: (CityResponse) -> Unit
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName: TextView = itemView.findViewById(R.id.city_name)
        val cityCountry: TextView = itemView.findViewById(R.id.city_country)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]
        holder.cityName.text = city.name
        holder.cityCountry.text = city.country
        holder.itemView.setOnClickListener {
            onCitySelected(city)
        }
    }

    override fun getItemCount(): Int = cities.size
}