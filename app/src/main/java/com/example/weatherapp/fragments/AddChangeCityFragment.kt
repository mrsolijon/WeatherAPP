package com.example.weatherapp.fragments

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentAddChangeCityBinding
import com.example.weatherapp.viewmodel.LocationViewModel

class AddChangeCityFragment : Fragment(R.layout.fragment_add_change_city) {

    private var _binding:FragmentAddChangeCityBinding? = null
    private val binding get()= _binding!!
    val viewModel = LocationViewModel()

    val apiKey = BuildConfig.WEATHER_API_KEY

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAddChangeCityBinding.bind(view)

        binding.addChangeCityBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
//        binding.searchCity(
//            object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//                        newText?.let {
//                            val trimmedText = it.trim()
//                            if (trimmedText.length > 2) {
//                                viewModel.searchCityCoordinates(trimmedText, requireContext(), apiKey)
//                            } else {
//                                binding.cityList.visibility = View.GONE
//                            }
//                        }
//                        return true
//                }
//            }
//        )
    }



    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null


    }



}