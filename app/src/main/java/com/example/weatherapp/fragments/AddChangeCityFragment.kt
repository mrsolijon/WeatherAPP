package com.example.weatherapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.adapter.CityRvAdapter
import com.example.weatherapp.databinding.FragmentAddChangeCityBinding
import com.example.weatherapp.model.CityResponse
import com.example.weatherapp.viewmodel.LocationViewModel
import com.example.weatherapp.viewmodel.WeatherViewModel

class AddChangeCityFragment : Fragment(R.layout.fragment_add_change_city) {

    private var _binding:FragmentAddChangeCityBinding? = null
    private val binding get()= _binding!!
    private lateinit var viewModel: LocationViewModel
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var cityAdapter: CityRvAdapter
//    val apiKey = BuildConfig.WEATHER_API_KEY

    private val filteredCityList = mutableListOf<CityResponse>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddChangeCityBinding.bind(view)

        viewModel = ViewModelProvider(requireActivity())[LocationViewModel::class.java]
        weatherViewModel = ViewModelProvider(requireActivity())[WeatherViewModel::class.java]

        binding.addChangeCityBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cityList.layoutManager = LinearLayoutManager(context)
        cityAdapter = CityRvAdapter(filteredCityList) { city ->
            Log.d("Shaxar adapterda", "Tanlandi: $city")
            viewModel.updateSelectedCity(city.name, city.lat, city.lon)
            weatherViewModel.loadWeatherData(city.lat, city.lon)
            findNavController().popBackStack()
        }
        binding.cityList.adapter = cityAdapter

        filteredCityList.clear()
        filteredCityList.addAll(cityList)
        cityAdapter.notifyDataSetChanged()

        binding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(text: String?): Boolean {
                    binding.cityList.visibility = View.VISIBLE
                    filterCities(text.orEmpty())
                    return true
                }
            }
        )
        binding.searchView.requestFocus()

    }

    private fun filterCities(query: String){

        filteredCityList.clear()
        if (query.isBlank()) {
            filteredCityList.addAll(cityList)
        } else {
            val filtered = cityList.filter { city ->
                city.name.lowercase().startsWith(query.lowercase())
            }
            filteredCityList.addAll(filtered)
        }
        Log.d("AddCityFragment", "Filtered list size: ${filteredCityList.size}")
        cityAdapter.notifyDataSetChanged()

    }

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null


    }

    private val cityList = listOf(
        CityResponse("Oqyop", 41.27051, 61.15344, "Uzbekistan"),
        CityResponse("Qo’shko’pir", 41.26318, 61.15479, "Uzbekistan"),
        CityResponse("Aloqaliko’l", 41.25855, 61.18816, "Uzbekistan"),
        CityResponse("Katta-Juvaixas", 41.28111, 61.21901, "Uzbekistan"),
        CityResponse("Ayrityop", 41.28613, 61.25642, "Uzbekistan"),
        CityResponse("Oyoqovul", 41.23406, 61.15691, "Uzbekistan"),
        CityResponse("Oyoqova", 41.1845, 61.27417, "Uzbekistan"),
        CityResponse("Sayatlar", 41.18836, 61.31486, "Uzbekistan"),
        CityResponse("Xidirov Nomli", 41.63615, 60.30069, "Uzbekistan"),
        CityResponse("Chudzha", 40.92935, 71.8703, "Uzbekistan"),
        CityResponse("Chimgon", 41.54962, 70.02479, "Uzbekistan"),
        CityResponse("Chimgon cable car station", 41.5255, 70.02083, "Uzbekistan"),
        CityResponse("Andijon", 40.6078, 68.24331, "Uzbekistan"),
        CityResponse("Shahrixon", 40.71331, 72.05706, "Uzbekistan"),
        CityResponse("Asaka", 40.64153, 72.23868, "Uzbekistan"),
        CityResponse("Bo‘z", 40.68722, 71.92333, "Uzbekistan"),
        CityResponse("Poytug‘", 40.8977, 72.2449, "Uzbekistan"),
        CityResponse("Xonobod", 40.8026, 72.97499, "Uzbekistan"),
        CityResponse("Namangan", 40.9983, 71.67257, "Uzbekistan"),
        CityResponse("London", 51.3030, 0.7320, "United Kingdom")

        )

}