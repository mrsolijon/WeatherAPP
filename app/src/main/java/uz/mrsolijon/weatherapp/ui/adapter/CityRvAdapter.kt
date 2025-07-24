package uz.mrsolijon.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.data.remote.model.CityResponse

class CityRvAdapter(
    private val cityList: List<CityResponse>,
    private val onCityClick: (CityResponse) -> Unit
) : RecyclerView.Adapter<CityRvAdapter.CityViewHolder>() {


    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName: TextView = itemView.findViewById(R.id.city_name)
        val cityCountry: TextView = itemView.findViewById(R.id.city_country)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cityList[position]
        holder.cityName.text = city.name
        holder.cityCountry.text = city.country
        holder.itemView.setOnClickListener {
            onCityClick(city)
        }
    }

    override fun getItemCount(): Int = cityList.size


}