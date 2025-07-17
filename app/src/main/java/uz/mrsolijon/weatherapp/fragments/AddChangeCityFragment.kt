package uz.mrsolijon.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.adapter.CityRvAdapter
import uz.mrsolijon.weatherapp.databinding.FragmentAddChangeCityBinding
import uz.mrsolijon.weatherapp.model.CityResponse
import uz.mrsolijon.weatherapp.model.WeatherViewModelFactory
import uz.mrsolijon.weatherapp.viewmodels.LocationViewModel
import uz.mrsolijon.weatherapp.viewmodels.WeatherViewModel

import java.util.Locale

class AddChangeCityFragment : Fragment(R.layout.fragment_add_change_city) {

    private var _binding: FragmentAddChangeCityBinding? = null
    private val binding get() = _binding!!
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var weatherViewModel: WeatherViewModel

    private lateinit var cityAdapter: CityRvAdapter

    private val cityList = listOf(
        CityResponse("Dubai", 25.07725, 55.30927, "UAE"),
        CityResponse("Paris", 48.85341, 2.3488, "France"),
        CityResponse("Moscow", 55.75222, 37.61556, "Russia"),
        CityResponse("New York", 40.71427, -74.00597, "USA"),
        CityResponse("Istanbul", 41.01384, 28.94966, "Turkey"),
        CityResponse("Astana", 51.1801, 71.44598, "Kazakhstan"),
        CityResponse("Berlin", 52.52437, 13.41053, "Germany"),
        CityResponse("Madrid", 40.4165, -3.70256, "Spain"),
        CityResponse("Munich", 48.13743, 11.57549, "Germany"),
        CityResponse("Seoul", 37.566, 126.9784, "South Korea"),
        CityResponse("Tokyo", 35.6895, 139.69171, "Japan"),
        CityResponse("Toshkent", 41.26465, 69.21627, "Uzbekistan"),
        CityResponse("Andijon", 40.6078, 68.24331, "Uzbekistan"),
        CityResponse("Shahrixon", 40.71331, 72.05706, "Uzbekistan"),
        CityResponse("Asaka", 40.64153, 72.23868, "Uzbekistan"),
        CityResponse("Bo‘z", 40.68722, 71.92333, "Uzbekistan"),
        CityResponse("Poytug‘", 40.8977, 72.2449, "Uzbekistan"),
        CityResponse("Xonobod", 40.8026, 72.97499, "Uzbekistan"),
        CityResponse("Namangan", 40.9983, 71.67257, "Uzbekistan"),
        CityResponse("London", 51.3030, 0.7320, "United Kingdom"),
        CityResponse("Farg'ona", 40.38421, 71.78432, "Uzbekistan"),
        CityResponse("Samarqand", 39.65417, 66.95972, "Uzbekistan"),
        CityResponse("Guliston", 40.48972, 68.78417, "Uzbekistan"),
        CityResponse("Buxoro", 39.77472, 64.42861, "Uzbekistan"),
        CityResponse("Qarshi", 38.86056, 65.78905, "Uzbekistan"),
        CityResponse("Xiva", 41.38555, 60.36408, "Uzbekistan"),
        CityResponse("Urganch", 41.55339, 60.62057, "Uzbekistan")
    )

    private val filteredCityList = mutableListOf<CityResponse>().apply { addAll(cityList) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddChangeCityBinding.bind(view)

        locationViewModel = ViewModelProvider(requireActivity())[LocationViewModel::class.java]
        val factory = WeatherViewModelFactory(requireActivity().application)
        weatherViewModel = ViewModelProvider(requireActivity(), factory)[WeatherViewModel::class.java]


        setupRecyclerView()
        setupSearchView()

        binding.addChangeCityBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                locationViewModel.locationData.collectLatest { locationInfo ->
//                    // Lokatsiya o'zgarganda bu yerda ishlarni bajaring
//                    // Masalan, tanlangan shaharga qarab UI ni yangilash
//                    Log.d("AddChangeCityFragment", "Location updated: ${locationInfo.city}")
//                }
//            }
//        }
    }

    private fun setupRecyclerView() {
        cityAdapter = CityRvAdapter(filteredCityList) { city ->
            locationViewModel.updateSelectedCity(city.name, city.lat, city.lon)
            findNavController().popBackStack()
        }
        binding.cityList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cityAdapter
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.cityList.visibility = View.VISIBLE
                filterCities(newText)
                return true
            }
        })
    }

    private fun filterCities(query: String?) {
        filteredCityList.clear()
        if (query.isNullOrBlank()) {
            filteredCityList.addAll(cityList)
        } else {
            val lowerCaseQuery = query.lowercase(Locale.getDefault())
            filteredCityList.addAll(cityList.filter {
                it.name.lowercase(Locale.getDefault()).contains(lowerCaseQuery) ||
                        it.country.lowercase(Locale.getDefault()).contains(lowerCaseQuery)
            })
        }
        cityAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null


    }


}