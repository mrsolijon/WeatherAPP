package uz.mrsolijon.weatherapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.ui.adapter.CityRvAdapter
import uz.mrsolijon.weatherapp.databinding.FragmentAddChangeCityBinding
import uz.mrsolijon.weatherapp.data.remote.model.CityResponse
import uz.mrsolijon.weatherapp.ui.viewmodel.LocationViewModel
import java.util.Locale
import androidx.fragment.app.activityViewModels

@AndroidEntryPoint
class AddChangeCityFragment : Fragment(R.layout.fragment_add_change_city) {

    private var _binding: FragmentAddChangeCityBinding? = null
    private val binding get() = _binding!!
    private val locationViewModel: LocationViewModel by activityViewModels()

    private lateinit var cityAdapter: CityRvAdapter

    private val cityList = listOf(
        CityResponse("Dubai", 25.07725, 55.30927, "UAE"),
        CityResponse("Paris", 48.85341, 2.3488, "France"),
        CityResponse("Moscow", 55.75222, 37.61556, "Russia"),
        CityResponse("Tokyo", 35.6895, 139.6917, "Japan"),
        CityResponse("New York", 40.7128, -74.0060, "USA"),
        CityResponse("London", 51.5074, 0.1278, "UK"),
        CityResponse("Beijing", 39.9042, 116.4074, "China"),
        CityResponse("Rome", 41.9028, 12.4964, "Italy"),
        CityResponse("Sydney", -33.8688, 151.2093, "Australia"),
        CityResponse("Cairo", 30.0444, 31.2357, "Egypt"),
        CityResponse("Rio de Janeiro", -22.9068, -43.1729, "Brazil"),
        CityResponse("Berlin", 52.5200, 13.4050, "Germany"),
        CityResponse("Istanbul", 41.0082, 28.9784, "Turkey"),
        CityResponse("Madrid", 40.4168, -3.7038, "Spain"),
        CityResponse("Mexico City", 19.4326, -99.1332, "Mexico"),
        CityResponse("Seoul", 37.5665, 126.9780, "South Korea"),
        CityResponse("Shanghai", 31.2304, 121.4737, "China"),
        CityResponse("Bangkok", 13.7563, 100.5018, "Thailand"),
        CityResponse("Buenos Aires", -34.6037, -58.3816, "Argentina"),
        CityResponse("Toronto", 43.651070, -79.347015, "Canada"),
        CityResponse("Mumbai", 19.0760, 72.8777, "India"),
        CityResponse("Cape Town", -33.9249, 18.4241, "South Africa"),
        CityResponse("Amsterdam", 52.3676, 4.9041, "Netherlands"),
        CityResponse("Prague", 50.0755, 14.4378, "Czech Republic"),
        CityResponse("Vienna", 48.2082, 16.3738, "Austria"),
        CityResponse("Warsaw", 52.2297, 21.0122, "Poland"),
        CityResponse("Dublin", 53.3498, -6.2603, "Ireland"),
        CityResponse("Lisbon", 38.7223, -9.1393, "Portugal"),
        CityResponse("Copenhagen", 55.6761, 12.5683, "Denmark"),
        CityResponse("Helsinki", 60.1695, 24.9354, "Finland"),
        CityResponse("Oslo", 59.9139, 10.7522, "Norway"),
        CityResponse("Stockholm", 59.3293, 18.0686, "Sweden"),
        CityResponse("Zurich", 47.3769, 8.5417, "Switzerland"),
        CityResponse("Brussels", 50.8503, 4.3517, "Belgium"),
        CityResponse("Singapore", 1.3521, 103.8198, "Singapore"),
        CityResponse("Kuala Lumpur", 3.1390, 101.6869, "Malaysia"),
        CityResponse("Jakarta", -6.2088, 106.8456, "Indonesia"),
        CityResponse("Manila", 14.5995, 120.9842, "Philippines"),
        CityResponse("Ho Chi Minh City", 10.8231, 106.6297, "Vietnam"),
        CityResponse("Dhaka", 23.8103, 90.4125, "Bangladesh"),
        CityResponse("Karachi", 24.8607, 67.0011, "Pakistan"),
        CityResponse("Lahore", 31.5498, 74.3436, "Pakistan"),
        CityResponse("Tehran", 35.6892, 51.3890, "Iran"),
        CityResponse("Riyadh", 24.7136, 46.6753, "Saudi Arabia"),
        CityResponse("Doha", 25.2854, 51.5310, "Qatar"),
        CityResponse("Abu Dhabi", 24.4539, 54.3773, "UAE"),
        CityResponse("Kuwait City", 29.3759, 47.9774, "Kuwait"),
        CityResponse("Baghdad", 33.3152, 44.3661, "Iraq"),
        CityResponse("Damascus", 33.5130, 36.2913, "Syria"),
        CityResponse("Beirut", 33.8938, 35.5018, "Lebanon"),
        CityResponse("Amman", 31.9539, 35.9106, "Jordan"),
        CityResponse("Jerusalem", 31.7683, 35.2137, "Israel"),
        CityResponse("Addis Ababa", 9.0054, 38.7636, "Ethiopia"),
        CityResponse("Nairobi", -1.2921, 36.8219, "Kenya"),
        CityResponse("Lagos", 6.5244, 3.3792, "Nigeria"),
        CityResponse("Accra", 5.6037, -0.1870, "Ghana"),
        CityResponse("Dakar", 14.6928, -17.4467, "Senegal"),
        CityResponse("Casablanca", 33.5731, -7.5898, "Morocco"),
        CityResponse("Tunis", 36.8065, 10.1815, "Tunisia"),
        CityResponse("Algiers", 36.7372, 3.0865, "Algeria"),
        CityResponse("Tripoli", 32.8872, 13.1913, "Libya"),
        CityResponse("Khartoum", 15.5007, 32.5599, "Sudan"),
        CityResponse("Giza", 30.0131, 31.2089, "Egypt"),
        CityResponse("Alexandria", 31.2001, 29.9187, "Egypt"),
        CityResponse("Ankara", 39.9334, 32.8597, "Turkey"),
        CityResponse("Baku", 40.4093, 49.8671, "Azerbaijan"),
        CityResponse("Tbilisi", 41.7151, 44.8271, "Georgia"),
        CityResponse("Yerevan", 40.1792, 44.4991, "Armenia"),
        CityResponse("Tashkent", 41.2995, 69.2401, "Uzbekistan"),
        CityResponse("Samarkand", 39.6542, 66.9597, "Uzbekistan"),
        CityResponse("Bukhara", 39.7670, 64.4560, "Uzbekistan"),
        CityResponse("Urgench", 41.5451, 60.6300, "Uzbekistan"),
        CityResponse("Andijan", 40.7812, 72.3413, "Uzbekistan"),
        CityResponse("Fergana", 40.3879, 71.7864, "Uzbekistan"),
        CityResponse("Namangan", 40.9990, 71.5647, "Uzbekistan"),
        CityResponse("Nukus", 42.4500, 59.6167, "Uzbekistan"),
        CityResponse("Termez", 37.2281, 67.2783, "Uzbekistan"),
        CityResponse("Qarshi", 38.8642, 65.7533, "Uzbekistan"),
        CityResponse("Jizzakh", 40.1264, 67.8776, "Uzbekistan"),
        CityResponse("Navoiy", 40.0844, 65.3789, "Uzbekistan"),
        CityResponse("Uralsk", 51.2291, 51.3702, "Kazakhstan"),
        CityResponse("Astana", 51.169392, 71.449074, "Kazakhstan"),
        CityResponse("Almaty", 43.222015, 76.851248, "Kazakhstan"),
        CityResponse("Bishkek", 42.8746, 74.5698, "Kyrgyzstan"),
        CityResponse("Dushanbe", 38.5606, 68.7712, "Tajikistan"),
        CityResponse("Ashgabat", 37.9601, 58.3260, "Turkmenistan")
    )
    private val filteredCityList = mutableListOf<CityResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filteredCityList.addAll(cityList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddChangeCityBinding.bind(view)
        setupRecyclerView()
        setupSearchView()
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