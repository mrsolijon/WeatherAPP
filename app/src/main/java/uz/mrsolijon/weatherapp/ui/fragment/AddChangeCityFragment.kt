package uz.mrsolijon.weatherapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.data.remote.model.CityResponse
import uz.mrsolijon.weatherapp.databinding.FragmentAddChangeCityBinding
import uz.mrsolijon.weatherapp.ui.adapter.CityRvAdapter
import uz.mrsolijon.weatherapp.ui.viewmodel.LocationViewModel

@AndroidEntryPoint
class AddChangeCityFragment : Fragment(R.layout.fragment_add_change_city) {

    private var _binding: FragmentAddChangeCityBinding? = null
    private val binding get() = _binding!!

    private val locationViewModel: LocationViewModel by activityViewModels()

    private lateinit var cityAdapter: CityRvAdapter

    private val filteredCityList = mutableListOf<CityResponse>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddChangeCityBinding.bind(view)

        setupRecyclerView()
        setupSearchView()
        observeSearchResults()

        binding.addChangeCityBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
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
                locationViewModel.searchCities(newText.orEmpty())
                return true
            }
        })
    }

    private fun observeSearchResults() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                locationViewModel.searchResults.collectLatest { cities ->
                    filteredCityList.clear()
                    filteredCityList.addAll(cities)
                    cityAdapter.notifyDataSetChanged()
                    if (cities.isEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.city_not_found),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}